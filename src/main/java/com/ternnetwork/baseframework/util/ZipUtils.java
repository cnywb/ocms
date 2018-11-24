package com.ternnetwork.baseframework.util;
import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;  
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;  
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;


public final class ZipUtils {
	public static void main(String[] args) {  
		  
		try {
			
			ZipUtils.getInstance().zip("D:/ssl/","D:/ssl.zip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
       
    }   
    private int bufferLen = 1024 * 1024;

    /**
     * 私有化构造方法，prevents calls from subclass
     */
    private ZipUtils() {
        throw new UnsupportedOperationException();
    }

    private ZipUtils(int bufferLen) {
        this.bufferLen = bufferLen;
    }

    public static ZipUtils getInstance() {
        return new ZipUtils(1024 * 1024);
    }

    public static ZipUtils getInstance(int bufferLen) {
        return new ZipUtils(bufferLen);
    }

    /**
     * 用于单文件压缩
     */
    protected void doCompress(File srcFile, File destFile) throws IOException {
        ZipArchiveOutputStream out = null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(srcFile), bufferLen);
            out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), bufferLen));
            ZipArchiveEntry entry = new ZipArchiveEntry(srcFile.getName());
            entry.setSize(srcFile.length());
            out.putArchiveEntry(entry);
            IOUtils.copy(is, out);
         
            out.closeArchiveEntry();
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 解压文件
     * @param zipFile
     * @param outDir
     * @throws IOException
     * 90s
     */
    public void doDecompress(String zipFilePath, String outDirStr) throws IOException {
        File zipFile = new File(zipFilePath);
        File outDir = new File(outDirStr);
        ZipArchiveInputStream is = null;
        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), bufferLen));
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(outDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(outDir, entry.getName())),
                                bufferLen);
                        IOUtils.copy(is, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     *  解压指定zip文件到目录,使用新方法
     * @param destFile 输出到的目录,此目录如果不存在会自动创建
     * @param srcFile 需要解压的zip文件名
     * @throws IOException
     * 30s
     */
    public static void unZip(String srcFile,String destFile) throws IOException {
        FileOutputStream fileOutStream = null;
        InputStream inputStream = null;
        ZipFile zipFile = null;
        File outDir = new File(destFile);
        File unZipfile = new File(srcFile);

        if (!unZipfile.exists()) {
            throw new FileNotFoundException("File to upzip:" + srcFile + " not found!");
        }
        try {
            zipFile = new ZipFile(unZipfile);
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipArchiveEntry entry = (ZipArchiveEntry) e.nextElement();
                File file = new File(outDir, entry.getName());
                //File file = getRealFileName(outDirPath, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    // 如果指定文件的目录不存在,则创建之.
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    try {
                        inputStream = zipFile.getInputStream(entry);
                        fileOutStream = new FileOutputStream(file);
                        IOUtils.copy(inputStream, fileOutStream);
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                        IOUtils.closeQuietly(fileOutStream);
                    }
                }
            }
        } finally {
            ZipFile.closeQuietly(zipFile);
            IOUtils.closeQuietly(inputStream);
        }

    }

    /**
     * 从文件中解压一个指定文件
     * @param fileName
     * @param zipFile
     * @return
     * @throws IOException 
     * @throws ZipException 
     */
    public InputStream readFileFromSingleFile(ZipFile zipFile, ZipArchiveEntry entry) throws IOException {
        InputStream inputStream = zipFile.getInputStream(entry);
        return inputStream;
    }

    /**
     * 把一个目录打包到一个指定的zip文件中
     * @param dirStr           目录绝对地址
     * @param pathName       zip文件内相对结构
     * @throws IOException 
     */
    private void packFiles(ZipArchiveOutputStream out, File dir, String pathName) throws IOException {
        InputStream is = null;
        //返回此绝对路径下的文件
        File[] files = dir.listFiles();
        if (files == null || files.length < 1) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            File zFile = files[i];
            out.putArchiveEntry(new ZipArchiveEntry(zFile, pathName + zFile.getName()));
            //判断此文件是否是一个文件夹
            if (zFile.isDirectory()) {
                packFiles(out, zFile, pathName + zFile.getName() + "/");
            } else {
                try {
                    //out.putArchiveEntry(new ZipArchiveEntry(pathName + files[i].getName()));
                    is = new BufferedInputStream(new FileInputStream(zFile), bufferLen);
                    IOUtils.copy(is, out);
                } finally {
                    is.close();
                }
            }
        }
    }

    /**
     * 压缩文件或者文件夹
     * @param srcFileStr 待压缩文件或文件夹路径
     * @param destFileStr 目标文件路径
     * @throws IOException
     */
    public void zip(String srcFileStr, String destFileStr) throws IOException {
        File destFile = new File(destFileStr);
        File srcFile = new File(srcFileStr);
        zip(srcFile, destFile);
    }

    /**
     * 压缩文件
     * @param srcFile 待压缩文件或文件夹
     * @param destFile目标压缩文件
     * @throws IOException
     */
    public void zip(File srcFile, File destFile) throws IOException {
        ZipArchiveOutputStream out = null;

        // 如果压缩文件存放目录不存在,则创建之.
        File pfile = destFile.getParentFile();
        if (!pfile.exists()) {
            pfile.mkdirs();
        }
        //如果是文件
        if (srcFile.isFile()) {
            doCompress(srcFile, destFile);
            return;
        } else {
            try {
                out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), bufferLen));
                packFiles(out, srcFile, "");
                out.closeArchiveEntry();
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
    }

    /**
     * 将压缩文件中部分文件压缩到另外一个文件中
     */
  /*  public void copyEntryToAnother(ZipFile srcFile, File destFile, ArrayList<ZipArchiveEntry> entryList)
            throws IOException {
        ZipArchiveOutputStream out = null;
        FileOutputStream fileOutStream = null;
        InputStream inputStream = null;

        //建立临时目录
        String tempDirStr = System.getProperty("java.io.tmpdir") + "temp"
                + File.separatorChar;
        File tempDir = new File(tempDirStr, String.valueOf(FlowNoGenerator.instance().getFlowNo()));
        tempDir.mkdirs();

        try {
            //解压
            for (ZipArchiveEntry entry : entryList) {
                File file = new File(tempDir, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    // 如果指定文件的目录不存在,则创建之.
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    try {
                        inputStream = srcFile.getInputStream(entry);
                        fileOutStream = new FileOutputStream(file);
                        IOUtils.copy(inputStream, fileOutStream);
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                        IOUtils.closeQuietly(fileOutStream);
                    }
                }
            }
            //压缩
            zip(tempDir, destFile);
        } finally {
            ExtendedFileUtils.deleteQuietly(tempDir);
        }
        IOUtils.closeQuietly(out);
    }*/
 

}