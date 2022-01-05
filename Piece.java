package com.chess.engine.pieces;

import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {
	
	protected final int piecePosition;
	
	protected final Alliance pieceAlliance;
	
	protected final boolean isFirstMove; 
	
	
	Piece(final int piecePosition, final Alliance pieceAlliance){
		
		this.pieceAlliance = pieceAlliance;
		
		this.piecePosition = piecePosition;
		
		this.isFirstMove = false;
		
	}
	
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	public boolean isFirstMove() {
		
		return this.isFirstMove;
	}
	
	public Alliance getPieceAlliance() {
		
		return this.pieceAlliance;
	}
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	//takes a given board and calculates a piece's legal move
	//piece is not instantiable so rather we are calculating legal moves
	//for a given piece type, such as a knight or a rook


	public enum PieceType {
		
		PAWN("P"),
		KNIGHT("N"),
		BISHOP("B"),
		QUEEN("Q"),
		KING("K"),
		ROOK("R");
		
	
		
		
		private String pieceName;
		
		PieceType( String pieceName)  {
			
			this.pieceName = pieceName;
		}
		
		@Override
		public  String toString() {
			
			return this.pieceName;
		}
		
	}

}
