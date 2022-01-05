package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class King extends Piece{
	private final static int[] CANDIDATE_MOVE_COORDINATE = {-8, 8, -1, 1, -7, 7, -9, 9};


	public King(int piecePosition, Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public
	 String toString() {
		return PieceType.KING.toString();
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		int candidateDestinationCoord;
		
		for (final int currentCandidateOffset  : CANDIDATE_MOVE_COORDINATE) {
			
			candidateDestinationCoord = this.piecePosition + currentCandidateOffset;
			
			if((isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
					isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)))  {
				continue;
			}
			
			
			if(BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
				
				
				if (!candidateDestinationTile.isTileOccupied()) {
					 
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
					
				}
				else {
					final Piece pieceAtLocation = candidateDestinationTile.getPiece();
					
					final Alliance pieceAlliance = pieceAtLocation.getPieceAlliance();
					
					if (this.pieceAlliance != pieceAlliance) {
						
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoord, pieceAtLocation));
					}
				}
			
			}
		}
		
		
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -1) || (candidateOffset == -9)
		|| (candidateOffset == 7));
			
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset == 1 || candidateOffset == 9
				|| candidateOffset == -7));
	}
	
}
