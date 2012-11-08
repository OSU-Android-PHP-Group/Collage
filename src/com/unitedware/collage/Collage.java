package com.unitedware.collage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;

import com.unitedware.collage.exceptions.EmptyCollageException;

public class Collage {
	private static String subDirectory;

	static {
		Collage.subDirectory = new File(ImageAdapter.getCollageDirectory(),
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

	public Collection<File> getImages() {
		return this.images;
	}

	public File generate() throws EmptyCollageException, FileNotFoundException,
			IOException {
		// Do not attempt to generate if images is empty.
		if (this.images.size() < 1)
			throw new EmptyCollageException();

		// Create the collage image and return it.
		File generatedCollage = new File(Collage.subDirectory, "yay");
		IOUtils.copy(ServerClient.postCollage(this), new FileOutputStream(
				generatedCollage));
		return generatedCollage;
	}
}
