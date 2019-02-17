package br.unifesspa.cre.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DAO<T> {
	
	/**
	 * Returns true if the informed path has a file, and false otherwise.
	 * @param path
	 * @return
	 */
	public Boolean verifyPath(String path) {
		if(new File(path).exists())
			return true;
		else return false;
	}
	
	/**
	 * Save an Instance in a File
	 * @param object
	 * @param path
	 */
	public void save(T object, String path) {

		try {
			FileOutputStream saveFile = new FileOutputStream(path);
			ObjectOutputStream stream = new ObjectOutputStream(saveFile);
			stream.writeObject(object);
			stream.close();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	/**
	 * Restore an instance from file
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T restore(String path) {
		T object = null;
		try {
			FileInputStream restoredFile = new FileInputStream(path);
			ObjectInputStream stream = new ObjectInputStream(restoredFile);
			object = (T) stream.readObject();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}