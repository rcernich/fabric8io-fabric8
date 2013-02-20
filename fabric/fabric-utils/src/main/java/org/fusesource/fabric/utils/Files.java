/*
 * Copyright (C) FuseSource, Inc.
 *   http://fusesource.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.fusesource.fabric.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class Files {

	private static final int BUFFER_SIZE = 1024;

	/**
	 * Reads a {@link File} and returns a {@String}.
	 *
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String toString(File file, Charset charset) throws IOException {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		if (file == null) {
			throw new FileNotFoundException("No file specified");
		}
		try {
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int remaining;
			while ((remaining = fis.read(buffer)) > 0) {
				bos.write(buffer, 0, remaining);
			}
			if (charset != null) {
				return new String(bos.toByteArray(), charset);
			} else {
				return new String(bos.toByteArray());
			}

		} finally {
			Closeables.closeQuitely(fis);
			Closeables.closeQuitely(bos);
		}
	}

	/**
	 * Reads a {@link File} and returns a {@String}.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String toString(File file) throws IOException {
		return toString(file, null);
	}

	/**
	 * Writes {@link String} content to {@link File}.
	 *
	 * @param file
	 * @param content
	 * @param charset
	 * @throws IOException
	 */
	public static void writeToFile(File file, String content, Charset charset) throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter writer = null;
		try {
			if (file == null) {
				throw new FileNotFoundException("No file specified.");
			} else if (!file.exists() && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
				throw new FileNotFoundException("Could not find or create file:" + file.getName());
			}
			fos = new FileOutputStream(file);
			writer = new OutputStreamWriter(fos, charset);
			writer.write(content, 0, content.length());
			writer.flush();
		} finally {
			Closeables.closeQuitely(fos);
			Closeables.closeQuitely(writer);
		}
	}


	/**
	 * Writes {@link String} content to {@link File}.
	 *
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public static void writeToFile(File file, byte[] content) throws IOException {
		FileOutputStream fos = null;
		try {
			if (file == null) {
				throw new FileNotFoundException("No file specified.");
			} else if (!file.exists() && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
				throw new FileNotFoundException("Could not find or create file:" + file.getName());
			}
			fos = new FileOutputStream(file);
			fos.write(content);
			fos.flush();
		} finally {
			Closeables.closeQuitely(fos);
		}
	}


	public static void copy(File source, File target) throws IOException {
		if (source == null) {
			throw new IOException("Source file is null.");
		} else if (!source.exists()) {
			throw new IOException("Source file is does not exists.");
		} else if (target == null) {
			throw new IOException("Target file is null.");
		} else if (!target.exists()) {
			if (source.isDirectory() && !target.mkdirs()) {
				throw new IOException("Can't create target directory:" + target.getAbsolutePath());
			} else if (!source.isDirectory() && !target.getParentFile().exists() && !target.getParentFile().mkdirs()) {
				throw new IOException("Can't create target parent directory:" + target.getParentFile().getAbsolutePath());
			}
		}

		if (source.isDirectory() && !target.isDirectory()) {
			throw new IOException("Can't copy a directory into a file.");
		} else if (source.isDirectory() && target.isDirectory()) {
			for (File child : source.listFiles()) {
				copy(child, new File(target, child.getName()));
			}
		} else if (!source.isDirectory() && !target.isDirectory()) {
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(source);
				os = new FileOutputStream(target);
				copy(is, os);
			} finally {
				Closeables.closeQuitely(is);
				Closeables.closeQuitely(os);
			}
		} else {
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(source);
				os = new FileOutputStream(new File(target, source.getName()));
				copy(is, os);
			} finally {
				Closeables.closeQuitely(is);
				Closeables.closeQuitely(os);
			}
		}
	}

	public static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024 * 16];
		int n = 0;
		while (-1 != (n = in.read(buffer))) {
			out.write(buffer, 0, n);
			out.flush();
		}
	}
}
