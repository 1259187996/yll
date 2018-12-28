package com.yzz.lr.util;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaoyuan
 *
 */

/**
 * @author huilin
 *
 */
public class FileUtil {

	protected static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static String getExtension(File file) {
		String extension = null;
		if (file.isFile()) {
			String orifileName = file.getName();
			int extLoc = orifileName.lastIndexOf(".");
			if (extLoc != -1) {
				extension = orifileName.substring(extLoc + 1, orifileName.length());
			}
		}
		return extension;
	}

	/**
	 * 对一个文件获取md5值
	 * 
	 * @return md5串
	 */
	public static String getMD5(File file) {
		MessageDigest MD5 = null;
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = fileInputStream.read(buffer)) != -1) {
				MD5.update(buffer, 0, length);
			}
			fileInputStream.close();
			return new String(Hex.encodeHex(MD5.digest()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isExcel(File file) {
		boolean isOffice = false;
		String orifileName = file.getName();
		String extension = getExtension(file);
		extension = extension.toLowerCase();
		if (!orifileName.contains("$") && (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx"))) {
			isOffice = true;
		}
		return isOffice;
	}

	/**
	 * 判断一个文件是否是Xml文件
	 * 
	 * @return boolean
	 * @author Meizi
	 * @throws Exception
	 */
	public static boolean isXml(File file) {
		boolean isOffice = false;
		String extension = getExtension(file);
		extension = extension.toLowerCase();
		if (extension.equalsIgnoreCase("xml")) {
			isOffice = true;
		}
		return isOffice;
	}

	public static boolean isPdf(File file) {
		boolean isOffice = false;
		String extension = getExtension(file);
		extension = extension.toLowerCase();
		if (extension.equalsIgnoreCase("pdf")) {
			isOffice = true;
		}
		return isOffice;
	}

	public static boolean isWord(File file) {
		boolean isOffice = false;
		String orifileName = file.getName();
		String extension = getExtension(file);
		extension = extension.toLowerCase();
		if (!orifileName.contains("$") && (extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx"))) {
			isOffice = true;
		}
		return isOffice;
	}

	public static boolean isZip(File file) {
		boolean isZip = false;
		String orifileName = file.getName();
		int extLoc = orifileName.lastIndexOf(".");
		if (extLoc != -1) {
			String extension = orifileName.substring(extLoc + 1, orifileName.length());
			extension = extension.toLowerCase();
			if (extension.equals("zip")) {
				isZip = true;
			}
		}
		return isZip;
	}

	public static boolean isRar(File file) {
		boolean isRar = false;
		String extension = getExtension(file);
		extension = extension.toLowerCase();
		if (extension.equals("rar")) {
			isRar = true;
		}
		return isRar;
	}

	public static String getXmlNames(String folder) {
		File dir = new File(folder);
		File[] files = dir.listFiles();
		String xmlBody = "";
		for (File file : files) {
			String extension = getExtension(file);
			extension = extension.toLowerCase();
			if (extension.equals("xml")) {
				xmlBody += file.getName() + ",";
			}
		}
		if (xmlBody.length() > 1)
			xmlBody = xmlBody.substring(0, xmlBody.length() - 1);
		if (xmlBody.length() == 0)
			logger.info("pdf转xml文件名为空");
		return xmlBody;
	}



	/**
	 * 删除指定的所有文件
	 * 
	 * @author Meizi
	 * @throws IOException
	 * @throws Exception
	 */
	public static void deleteFiles(List<File> fileList) throws IOException {
		if (fileList == null || fileList.size() == 0) {
			return;
		}
		try {
			for (int i = 0; i < fileList.size(); i++) {
				File curFile = fileList.get(i);
				boolean isTrue = curFile.delete();
				// logger.info(fileList.get(i) + "是否删除成功：" + isTrue);
			}
		} catch (Exception e) {
			logger.error("删除指定文件失败：" + e, e);
			throw e;
		}
	}

	/**
	 * 递归删除指定目录下的所有xml文件
	 * 
	 * @param xmlPath
	 * @author Meizi
	 * @throws IOException
	 * @throws Exception
	 */
	public static void deleteXmlFiles(File xmlPath) throws IOException {
		File[] files = xmlPath.listFiles();
		if (files == null) {
			return;
		}
		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteXmlFiles(files[i]);
				} else if (files[i].isFile()) {
					File curFile = files[i];
					if (FileUtil.isXml(curFile)) {
						curFile.delete();
					}
				}
			}
		} catch (Exception e) {
			logger.error("删除xml出现异常：" + e, e);
			throw e;
		}
	}

	/**
	 * 递归删除指定目录下的所有png文件
	 * 
	 * @param pngPath
	 * @author Meizi
	 * @throws IOException
	 * @throws Exception
	 */
	public static void deletePngFiles(File pngPath) throws IOException {
		File[] files = pngPath.listFiles();
		if (files == null) {
			return;
		}
		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deletePngFiles(files[i]);
				} else if (files[i].isFile()) {
					File curFile = files[i];
					if (FileUtil.isPng(curFile)) {
						curFile.delete();
					}
				}
			}
		} catch (Exception e) {
			logger.error("删除png出现异常：" + e, e);
			throw e;
		}
	}

	/**
	 * 递归删除指定目录下包含指定特征的文件
	 * 
	 * @param dir
	 * @param feature
	 * @author Meizi
	 * @throws IOException
	 * @throws Exception
	 */
	public static void deleteFeatureFiles(File dir, String feature) throws IOException {
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteFeatureFiles(files[i], feature);
				} else if (files[i].isFile() && files[i].getName().contains(feature)) {
					File curFile = files[i];
					boolean isTrue = curFile.delete();
					logger.info(curFile.getName() + "是否删除成功：" + isTrue);
				}
			}
		} catch (Exception e) {
			logger.error("删除xml出现异常：" + e, e);
			throw e;
		}
	}

	public static File writeStringToFile5(String filePath, String content) throws Exception {
		File file = null;
		file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getName());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();

		if (file.equals(null)) {
			logger.info("字符串写入文件为空。");
		} else {
			logger.info("字符串写入文件成功");
		}
		return file;
	}

	public static boolean isPng(File file) {
		boolean isOffice = false;
		if (file.isFile()) {
			String orifileName = file.getName();
			int extLoc = orifileName.lastIndexOf(".");
			if (extLoc != -1) {
				String extension = orifileName.substring(extLoc + 1, orifileName.length());
				extension = extension.toLowerCase();
				if (extension.equalsIgnoreCase("png")) {
					isOffice = true;
				}
			}
		}
		return isOffice;
	}

	/**
	 * 从一个目录中获取所有OCR文件
	 * 
	 * @param dir
	 * @return List<File>
	 */
	public static List<File> getOcrFileList(File dir) {

		List<File> fileList = new ArrayList<>();
		File[] files = dir.listFiles();
		if (files == null) {
			return fileList;
		}

		for (int i = 0; i < files.length; i++) {
			File curFile = files[i];
			if (files[i].isDirectory()) {
				fileList.addAll(getOcrFileList(files[i]));
			} else if (FileUtil.isXml(curFile)) {
				fileList.add(curFile);

			}
		}
		return fileList;
	}

	/**
	 * 在指定路径下根据名称查找指定文件
	 * 
	 * @param dir
	 * @param name
	 */
	public static List<File> getFileByName(File dir, String name) {
		List<File> fileList = new ArrayList<>();
		File[] files = dir.listFiles();
		if (files == null) {
			return fileList;
		}

		for (int i = 0; i < files.length; i++) {
			File curFile = files[i];
			if (files[i].isDirectory()) {
				fileList.addAll(getFileByName(files[i], name));
			} else if (curFile.isFile()) {
				String fileName = curFile.getName();
				String fileNameNoExtension = fileName.substring(0, fileName.lastIndexOf("."));
				if (fileNameNoExtension.equals(name)) {
					fileList.add(curFile);
				}
			}
		}

		return fileList;
	}

	public static String getFileNameListStr(List<File> fileList) {
		String fileNameList = "";
		for (File file : fileList) {
			fileNameList = fileNameList + file.getName() + ",";
		}
		return fileNameList;
	}

	public static String getStringListStr(List<String> StringList) {
		String string = "";
		for (String str : StringList) {
			string = string + str + ",";
		}
		return string;
	}

	/**
	 * 删除路径下所有文件
	 * 
	 * @author Stella
	 * @param path
	 */
	public static void deleteAllFilesOfDir(File path) {
		try {
			if (path == null || !path.exists())
				return;
			if (path.isFile()) {
				boolean isDelete = path.delete();
				// logger.info("文件删除状态：" + isDelete);
				return;
			}
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteAllFilesOfDir(files[i]);
			}
			path.delete();
		} catch (Exception e) {
			logger.error("文件删除异常：" + path, e);
		}
	}

	/**
	 * 获取指定目录下的所有文件
	 * 
	 * @return
	 */
	public static List<File> getFilesExceptZip(File dir) {
		List<File> fileList = new ArrayList<>();
		File[] files = dir.listFiles();
		if (files == null) {
			return fileList;
		}

		for (int i = 0; i < files.length; i++) {
			File curFile = files[i];
			if (files[i].isDirectory()) {
				fileList.addAll(getFilesExceptZip(files[i]));
			} else if (!FileUtil.isRar(curFile) && !FileUtil.isZip(curFile)) {
				fileList.add(curFile);
			}
		}
		return fileList;
	}

	/**
	 * 获取目录下的所有压缩文件
	 * 
	 * @param dir
	 * @return
	 */
	public static List<File> getCompressedFiles(File dir) {
		List<File> fileList = new ArrayList<>();
		File[] files = dir.listFiles();
		if (files == null) {
			return fileList;
		}

		for (int i = 0; i < files.length; i++) {
			File curFile = files[i];
			if (curFile.isDirectory()) {
				fileList.addAll(getCompressedFiles(curFile));
			} else if (FileUtil.isRar(curFile) || FileUtil.isZip(curFile)) {
				fileList.add(curFile);
			} else if (curFile.isFile()) {
				fileList.add(curFile);
			} else {
				continue;
			}
		}
		return fileList;
	}

	/**
	 * @param pdfName
	 * @return
	 */
	public static String getPageNo(String pdfName) {
		String pagno = pdfName.substring(pdfName.length() - 5, pdfName.length() - 4);
		String regex = "_([0-9]+)\\.";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pdfName);
		if (matcher.find()) {
			pagno = matcher.group(1);
		}
		return pagno;
	}

	public static void main(String[] args) throws Exception {
		String png = "218b3ec8b358033692525a06efeb47ae0000_21.png";
		System.out.println(getPageNo(png));

	}

	/**
	 * 下面开始，@author wellin
	 */

	/**
	 * 写map数据 每行数据为 strKey{objKey1=objValue1,objKey2=objValue2,....}
	 * 
	 * @param path
	 * @param map
	 */
	public static void writeData(String path, Map<String, Map<Object, Object>> map) {
		List<String> list = new ArrayList<>();
		for (String strKey : map.keySet()) {
			StringBuffer line = new StringBuffer(strKey);
			Map<Object, Object> tmap = map.get(strKey);
			line.append(tmap.toString());
			list.add(line.toString());
		}
		writeData(path, list);
	}

	public static void writeData(String path, Set<String> set) {
		List<String> list = new ArrayList<>();
		list.addAll(set);
		writeData(path, list);
	}

	/**
	 * 写入数据
	 * 
	 * @param path
	 * @param list
	 */
	public static void writeData(String path, List<String> list) {
		File file = new File(path);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			for (String s : list) {
				bw.write(s + "\n");
			}
		} catch (IOException e) {
			logger.error(path + "数据写入出错！", e);
		} finally {
			closeWriter(bw);
		}
		logger.info(path + "数据写入完成！");
	}

	public static Set<String> getSetData(String path) {
		Set<String> set = new TreeSet<>();
		List<String> list = getListData(path);
		set.addAll(list);
		return set;
	}

	public static Map<String, Map<String, String>> getMapData(String path) {
		InputStream in;
		try {
			in = new FileInputStream(path);
			return getMapData(in);
		} catch (FileNotFoundException e) {
			logger.error(path + "数据获取出错！", e);
		}
		return new TreeMap<>();
	}

	/**
	 * 获取map数据 每行数据为 strKey{objKey1=objValue1,objKey2=objValue2,....}
	 * 
	 * @return
	 */
	public static Map<String, Map<String, String>> getMapData(InputStream inputStream) {
		Map<String, Map<String, String>> map = new TreeMap<>();
		List<String> list = getListData(inputStream);
		for (String line : list) {
			String[] lines = line.split("\\{");
			if (lines.length != 2) {
				continue;
			}
			String key = lines[0];
			String[] mapstr = lines[1].split(",|\\}");
			if (mapstr.length < 1) {
				continue;
			}
			Map<String, String> tmap = new TreeMap<>();
			for (String pair : mapstr) {
				String[] pairs = pair.split("=");
				if (pairs.length != 2) {
					continue;
				}
				tmap.put(pairs[0].trim(), pairs[1].trim());
			}
			if (!tmap.isEmpty()) {
				map.put(key, tmap);
			}
		}
		return map;
	}

	/**
	 * 获取行数据
	 * 
	 * @param path
	 * @return：list
	 */
	public static List<String> getListData(String path) {
		List<String> list = new ArrayList<String>();
		File file = new File(path);
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			while ((line = br.readLine()) != null) {
				// if (line.trim().length() > 0){
				list.add(line.trim());
				// }
			}
		} catch (Exception e) {
			logger.error(path + "文件数据读取出错！", e);
		} finally {
			closeReader(br);
		}
		logger.info(path + "文件数据读取完成！");
		return list;
	}

	/**
	 * 获取行数据
	 * 
	 * @return：list
	 */
	public static List<String> getListData(InputStream inputStream) {
		List<String> list = new ArrayList<String>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					list.add(line.trim());
				}
			}
		} catch (Exception e) {
			logger.error("文件数据读取出错！", e);
		} finally {
			closeReader(br);
		}
		logger.info("文件数据读取完成！");
		return list;
	}

	/**
	 * 关闭读取文件流
	 * 
	 * @param br
	 */
	private static void closeReader(BufferedReader br) {
		try {
			br.close();
		} catch (IOException e) {
			logger.error("文件流关闭出错！", e);
		}
	}

	private static void closeWriter(BufferedWriter bw) {
		try {
			bw.close();
		} catch (IOException e) {
			logger.error("文件流关闭出错！", e);
		}
	}

	private static void closeInStream(InputStream in) {
		try {
			in.close();
		} catch (IOException e) {
			logger.error("文件流关闭出错！", e);
		}
	}

	private static void closeOutStream(OutputStream out) {
		try {
			out.close();
		} catch (IOException e) {
			logger.error("文件流关闭出错！", e);
		}
	}

	/**
	 * 复制文件
	 * 
	 */
	public static void copyFile(File oldFile, File newFile) {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			if (oldFile.exists()) {
				inStream = new FileInputStream(oldFile);
				fs = new FileOutputStream(newFile);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
			logger.info("文件copy成功！");
		} catch (Exception e) {
			logger.error("文件copy出错！", e);
		} finally {
			closeInStream(inStream);
			closeOutStream(fs);
		}
	}

	public static void copyFile(String oldPath, String newPath) {
		copyFile(new File(oldPath), new File(newPath));
	}

	public static void copyFile(File oldFile, String newPath) {
		copyFile(oldFile, new File(newPath));
	}

	public static void copyFile(String oldPath, File newFile) {
		copyFile(new File(oldPath), newFile);
	}

	/**
	 * 压缩文件。不对文件进行判断。由调用人员自行判断
	 * 
	 * @param file
	 * @return
	 */
	public File compressedFile(File file) {
		if (file == null) {
			return null;
		}

		return null;

	}

}
