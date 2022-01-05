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
import com.google.common.collect.ImmutableList;

public class Queen extends Piece {
	/* the queen's legal moves are equivalent to the unions of legals moves of the rook and the bishop
	 */
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9, -1, 8, -1, 1};

	public Queen(int piecePosition, Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}
	@Override
	public
	 String toString() {
		return PieceType.QUEEN.toString();
	}
	
	
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for (final int candidateCoordOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
			
			int candidateDestinationCoord = this.piecePosition;
			
			while(BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
				
				if(isFirstColumnExclusion(candidateDestinationCoord, candidateCoordOffset) ||
						isEighthColumnExclusion(candidateDestinationCoord, candidateCoordOffset)) {
					break;
				}
				
				candidateDestinationCoord += candidateCoordOffset;
				/* Loop through vectors in the list so long as they are legal tiles*/
				
				if (BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
					
					final Tile candidateTile = board.getTile(candidateDestinationCoord);
					
					if (!candidateTile.isTileOccupied()) {
						 
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
						
					}
					else {
						final Piece pieceAtLocation = candidateTile.getPiece();
						
						final Alliance pieceAlliance = pieceAtLocation.getPieceAlliance();
						
						if (this.pieceAlliance != pieceAlliance) {
							
							legalMoves.add(new AttackMove(board, this, candidateDestinationCoord, pieceAtLocation));
						}
						
						break;
						
						/* the purpose of this break is that once the bishop's path has been
						 * obstructed by another piece, there is no point in continuing down
						 * the current vector path since the rest of the path is also blocked
					
						 */

					}
					
					
					
					
				}
			}
		}
		
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7 ||
				candidateOffset == -1);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9
				|| candidateOffset == 1);
	}

	

}
