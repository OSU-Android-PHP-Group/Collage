package com.unitedware.collage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import com.unitedware.collage.exceptions.EmptyCollageException;

public class Collage {
	private static String subDirectory;

	static {
		Collage.subDirectory = new File(ImageAdapter.getCollageDirectory(),
				"collages").getPath();
	}

	private ArrayList<File> images;
	private File generated;

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

	public File getGenerated() {
		return this.generated;
	}

	/**
	 * Generate a collage with the given filename in this applications collages
	 * directory.
	 *
	 * @param fileName
	 *            The name to give the collage when stored in the collages
	 *            directory.
	 * @return The generated collage as a file.
	 * @throws EmptyCollageException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public File generate(String fileName) throws EmptyCollageException,
			FileNotFoundException, IOException {
		// Do not attempt to generate if images is empty.
		if (this.images.size() < 1)
			throw new EmptyCollageException();

		// Create the collage image and return it.
		File tmpCollage = new File(Collage.subDirectory, fileName);
		IOUtils.copy(ServerClient.postCollage(this), new FileOutputStream(
				tmpCollage));

		// Only set this.generated if postCollage and the copy function succeed
		// without exception.
		this.generated = tmpCollage;
		return this.generated;
	}

	/**
	 * Generate a collage in this applications collages directory with the
	 * current timestamp as the filename.
	 *
	 * @return The generated collage as a file.
	 * @throws FileNotFoundException
	 * @throws EmptyCollageException
	 * @throws IOException
	 */
	public File generate() throws FileNotFoundException, EmptyCollageException,
			IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		return this.generate(timeStamp + ".jpg");
	}
}
