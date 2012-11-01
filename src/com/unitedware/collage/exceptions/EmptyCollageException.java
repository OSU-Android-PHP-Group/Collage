package com.unitedware.collage.exceptions;

public class EmptyCollageException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public EmptyCollageException() {
		super("Cannot generate a collage from 0 images.");
	}
}
