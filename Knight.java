package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, 17, -15, 15, -10, 10, -6, 6};
	
	
	public Knight(int piecePosition, Alliance pieceAlliance) {
		
		super(piecePosition, pieceAlliance);
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public
	 String toString() {
		return PieceType.KNIGHT.toString();
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		/* we are actually calculating candidate legal moves
		 * there might be pieces in the way or other reasons we can't 
		 * move to the proposed squares such as a pin or being in check
		 * recall that we have enumerated tiles from 1 to 64
		 * candidate squares given that the knight is on tile x are:
		 * x+-6,x+-10,x+-15,x+-17
		 * Note that if the knight is in file a || b there is a different algorithm
		 * for calculating its legal moves
		 * 
		 */
		int candidateCoord;
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for (final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES) {
			
			candidateCoord = this.piecePosition + currentCandidateOffset;
			
			if(BoardUtils.isValidTileCoord(candidateCoord)) {
				
				if( 	isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)
						|| isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)
						|| isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset)
						|| isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue;
				}
				
				
				final Tile candidateTile = board.getTile(candidateCoord);
				
				if (!candidateTile.isTileOccupied()) {
					 
					legalMoves.add(new MajorMove(board, this, candidateCoord));
					
				}
				else {
					final Piece pieceAtLocation = candidateTile.getPiece();
					
					final Alliance pieceAlliance = pieceAtLocation.getPieceAlliance();
					
					if (this.pieceAlliance != pieceAlliance) {
						
						legalMoves.add(new AttackMove(board, this, candidateCoord, pieceAtLocation));
					}
				}
			}
			
			
		}
		
		
		
		
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -17) || (candidateOffset == -10)
		|| (candidateOffset == 6) || (candidateOffset == 15));
			
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset == -10 || candidateOffset == 6));
	}
	
	private static boolean isSeventhColumnExclusion (final int currentPosition, final int candidateOffset) {
		
	return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset == -6 || candidateOffset == -10)); 

	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == -15 || candidateOffset == -6)
				|| candidateOffset == 10 || candidateOffset == 17);
	}

}

