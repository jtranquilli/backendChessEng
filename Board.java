package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

public class Board {
	
	private final  List<Tile> gameBoard;
	
	private final Collection<Piece> whitePieces;	
	
	private final Collection<Piece> blackPieces;	
	
	
	private Board(Builder builder) {
		
		this.gameBoard = createGameBoard(builder);
		
		this.whitePieces =  calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
	
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
	
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			
			if((i+1) % 8 == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
		
	}
	
	private static String prettyPrint(Tile tile) {
		if (tile.isTileOccupied()) {
			return tile.getPiece().getPieceAlliance().isBlack() ? 
					tile.toString().toLowerCase() :
						tile.toString();
					
		}
		
		return tile.toString();
	}

	private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
		//recall that the other method by the same name calculates
		//legal moves for individual pieces, here we want to compile the
		//lists of total legals moves given the game state
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final Piece piece  : pieces) {
			piece.calculateLegalMoves(this);
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		
		
		return null;
	}

	private Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Alliance alliance) {
		
		final List<Piece> activePieces  = new ArrayList<>();
		
		for(final Tile tile : gameBoard) {
			if (tile.isTileOccupied()) {
				final Piece piece = tile.getPiece();
				if (piece.getPieceAlliance() == alliance) {
					activePieces.add(piece);
				}
				
			} 
		}
		return ImmutableList.copyOf(activePieces);
	}

	public Tile getTile(final int tileCoord) {
		
		return gameBoard.get(tileCoord);
	}
	
	private static List<Tile> createGameBoard(final Builder builder) {
		
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			
			tiles[i] = Tile.createTile(i, builder.boardConfiguration.get(i));
			
		}
		return ImmutableList.copyOf(tiles);
	}
	
	public static Board createStandardBoard() {
		// creates the starting position for the chess board
		//black occupies from 0 to 15, white from 48 to 63
		
		final Builder builder = new Builder();
		
		builder.setPiece(new Rook(0, Alliance.BLACK));
		builder.setPiece(new Knight(1, Alliance.BLACK));
		builder.setPiece(new Bishop(2, Alliance.BLACK));
		builder.setPiece(new Queen(3, Alliance.BLACK));
		builder.setPiece(new King(4, Alliance.BLACK));
		builder.setPiece(new Bishop(5, Alliance.BLACK));
		builder.setPiece(new Knight(6, Alliance.BLACK));
		builder.setPiece(new Rook(7, Alliance.BLACK));
		
		builder.setPiece(new Pawn(8, Alliance.BLACK));
		builder.setPiece(new Pawn(9, Alliance.BLACK));
		builder.setPiece(new Pawn(10, Alliance.BLACK));
		builder.setPiece(new Pawn(11, Alliance.BLACK));
		builder.setPiece(new Pawn(12, Alliance.BLACK));
		builder.setPiece(new Pawn(13, Alliance.BLACK));
		builder.setPiece(new Pawn(14, Alliance.BLACK));
		builder.setPiece(new Pawn(15, Alliance.BLACK));
		
		
		
		builder.setPiece(new Rook(56, Alliance.WHITE));
		builder.setPiece(new Knight(57, Alliance.WHITE));
		builder.setPiece(new Bishop(58, Alliance.WHITE));
		builder.setPiece(new Queen(59, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE));
		builder.setPiece(new Bishop(61, Alliance.WHITE));
		builder.setPiece(new Knight(62, Alliance.WHITE));
		builder.setPiece(new Rook(63, Alliance.WHITE));
		
		builder.setPiece(new Pawn(48, Alliance.WHITE));
		builder.setPiece(new Pawn(49, Alliance.WHITE));
		builder.setPiece(new Pawn(50, Alliance.WHITE));
		builder.setPiece(new Pawn(51, Alliance.WHITE));
		builder.setPiece(new Pawn(52, Alliance.WHITE));
		builder.setPiece(new Pawn(53, Alliance.WHITE));
		builder.setPiece(new Pawn(54, Alliance.WHITE));
		builder.setPiece(new Pawn(55, Alliance.WHITE));
		
		
		builder.setMoveMaker(Alliance.WHITE);
		
		return builder.build();
		
	}
	
	public static class Builder {
		
		Map<Integer, Piece> boardConfiguration;
		Alliance allianceToMove; 
		
		public Builder() {
			
			this.boardConfiguration = new HashMap<>();
			
			
		
		}
		
		Builder setPiece(final Piece piece) {
			this.boardConfiguration.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		public Builder setMoveMaker(final Alliance allianceToMove) {
			this.allianceToMove  = allianceToMove;
			return this;
		}
		
		public Board build() {
			
			return new Board(this);
		}
		
	}

}
