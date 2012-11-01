package com.unitedware.collage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import com.unitedware.collage.exceptions.EmptyCollageException;

public class Collage {
	private static String subDirectory;

	static {
		Collage.subDirectory = new File(
				ImageAdapter.getCollageDirectory(),
				"collages").getPath();
	}

	private ArrayList<File> images;

	public Collage() {
	    this.images = new ArrayList<File>();
	}

	public Collage(Collection<? extends File> images) {
		this.images = new ArrayList<File>(images);
	}

	public void addImages(Collection<? extends File> images) {
		this.images.addAll(images);
	}

	public void addImage(File image) {
		this.images.add(image);
	}

	public void removeImage(int index) {
		this.images.remove(index);
	}

	public void removeImage(File image) {
		this.images.remove(image);
	}

	public File generate() throws EmptyCollageException {
		if (this.images.size() > 1) {
			return new File(Collage.subDirectory, "yay");
		} else {
			throw new EmptyCollageException();
		}
	}
}
