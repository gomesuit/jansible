package jansible.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class JansibleZip {

	public static void zip(File zipFilePath, File srcDir) throws Exception {
		List<File> pathList = getFileList(srcDir);

		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
			encode(zos, pathList, srcDir);
		}
	}

	private static void encode(ZipOutputStream zos, List<File> pathList, File rootDir)throws Exception {
		byte[] buf = new byte[1024];

		for (File str : pathList) {
			ZipEntry entry = new ZipEntry(str.getPath().substring(rootDir.getPath().length() + 1).replace('\\', '/'));
			zos.putNextEntry(entry);
			try (InputStream is = new BufferedInputStream(new FileInputStream(str))) {
				for (;;) {
					int len = is.read(buf);
					if (len < 0)
						break;
					zos.write(buf, 0, len);
				}
			}
		}
	}

	private static List<File> getFileList(File file) {
		List<File> fileList = new ArrayList<>();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File item : files) {
				fileList.addAll(getFileList(item));
			}
		} else {
			fileList.add(file);
		}

		return fileList;
	}
}
