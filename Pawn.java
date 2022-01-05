package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16};

	
	public Pawn(final int piecePosition, final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
			}

	
	@Override
	public
	 String toString() {
		return PieceType.PAWN.toString();
	}
	
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE) {
			
			final int candidateDestinationCoord = this.piecePosition + currentCandidateOffset*(this.pieceAlliance.getDirection());
			
										/*note that black pawns and white pawns are moving in opposite directions
										 * which is captured by multiplying the move vector by this.pieceAlliance.getDirection()
										 */
			
			if(!BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
				continue;
			}
			
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoord).isTileOccupied()) {
				
				//define a pawn move here
				legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoord));
			}
			else if(currentCandidateOffset == 16 && this.isFirstMove() && (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack() || 
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite()))) {
				
				final int behindCandidateDestinationCoord = this.piecePosition+ (this.getPieceAlliance().getDirection()*8);
				
				if(!board.getTile(behindCandidateDestinationCoord).isTileOccupied() &&
					!board.getTile(candidateDestinationCoord).isTileOccupied()) {
					
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoord));
				}
			} else if (currentCandidateOffset == 7 &&
				!(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
				(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))
					{if (board.getTile(candidateDestinationCoord).isTileOccupied()) {
						final Piece pieceOnCandidate = board.getTile(candidateDestinationCoord).getPiece();
						if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
							legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoord));

						}
					}
				}
			else if (currentCandidateOffset == 9 &&
					!(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))
						{if (board.getTile(candidateDestinationCoord).isTileOccupied()) {
							final Piece pieceOnCandidate = board.getTile(candidateDestinationCoord).getPiece();
							if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
								legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoord));

							}
						}
					}
			
				}
				
			
		return ImmutableList.copyOf(legalMoves);
}

		
		
		
		
	}
	


