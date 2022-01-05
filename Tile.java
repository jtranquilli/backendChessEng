package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;

import java.util.Map;


public abstract class Tile {

	protected final int tileCoordinate;
	// this is made protected so that it can only be accessed by its subclasses
	//and final so that once the member field is set in the constructor it can't be set again
	/* the usage of final and private variables throughout this class
	 * is designed to make it immutable so that it can only be interacted 
	 * with and mutated in "approved" ways, this also mitigates the severity of object oriented
	 * spaghetti that can occur later on
	 */
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createtAllPossibleEmptyTiles();
	
	private Tile(int tileCoordinate) {
		
		this.tileCoordinate = tileCoordinate;
	}
	
	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
	}
	
	private static Map<Integer, EmptyTile> createtAllPossibleEmptyTiles() {
		// TODO Auto-generated method stub
		
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			
			emptyTileMap.put(i, new EmptyTile(i));
		}
		
		return ImmutableMap.copyOf(emptyTileMap);
		//this gives us a way of consistently referencing
		//any tile on the chess board
	}

	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile{
		
		private EmptyTile(final int coordinate) { //final by convention
			
			super(coordinate);
			
		}
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
		
		@Override
		public String toString() {
			return "-";
		}
	}
	
	public static final class OccupiedTile extends Tile {
		
		private final Piece pieceOnTile;
		
		private OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
			
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
		
		@Override
		public String toString() {
			    
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase()
			: getPiece().toString();
			
			//black pieces will be lower case
		}
	}
}
