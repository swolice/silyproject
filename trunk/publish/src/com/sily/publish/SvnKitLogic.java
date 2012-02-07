package com.sily.publish;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SvnKitLogic {
	
	public static void main(String[] args) {
		try {
			
//			
//			String ext_name = new File("E:/sily/Pictures/DSCN6999.JPG").getName();
//			System.out.println(ext_name.lastIndexOf("."));
//			ext_name = ext_name.substring(ext_name.lastIndexOf("."));
//			
//			System.out.println(ext_name);
//			
			String str = SvnKitLogic.process(new File("E:/movie/bj_dtxlt_111231.jpg"));
			System.out.println(str);
		} catch (SVNException e) {
			e.printStackTrace();
		}
	}
	

	public static final String url = "https://silyproject.googlecode.com/svn/trunk/ sily_file/images/";

	public static String process(File f) throws SVNException {

		SVNRepository repository = init();

		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMM");
		String nyString = sFormat.format(new Date());
		SVNNodeKind nodeKind = repository.checkPath(nyString, -1);
		
		String logMessage = "svnkit test log ";
		ISVNEditor editor = repository.getCommitEditor(logMessage,
				null /* locks */, true /* keepLocks */, null /* mediator */);
		
		String ext_name = f.getName();
		ext_name = ext_name.substring(ext_name.lastIndexOf("."));

		String nameString = System.currentTimeMillis() + ext_name;

		
		SVNCommitInfo svnCommitInfo = null;

		if (nodeKind == SVNNodeKind.NONE) {
			System.out.println("No entry at URL " + repository.getLocation());

			// 添加目录 文件
			svnCommitInfo = addDir(editor, nyString, nameString,
					getBytesFromFile(f));

			// System.exit( 1 );
		} else if (nodeKind == SVNNodeKind.FILE) {
			System.out.println("Entry at URL " + repository.getLocation()
					+ " is a file while directory was expected");

			// System.exit(1);
		} else {
			// 直接添加文件
			svnCommitInfo = addFile(editor, nyString, nameString,
					getBytesFromFile(f));

		}
		System.out.println(svnCommitInfo.getNewRevision());
		System.out.println(svnCommitInfo.getAuthor());
		
		return url + nyString + "/" + nameString;
	}

	private static SVNRepository init() {
		FSRepositoryFactory.setup();

		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIDecoded(url));

			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager("jishijun204@163.com",
							"Ce3sy3xB5Gc3");

			repository.setAuthenticationManager(authManager);

		} catch (SVNException e) {
			e.printStackTrace();
		}

		return repository;
	}

	private static byte[] getBytesFromFile(File f) {
		if (!f.exists()) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}

	private static SVNCommitInfo addDir(ISVNEditor editor, String dirPath,
			String filePath, byte[] data) throws SVNException {

		editor.openRoot(-1);

		editor.addDir(dirPath, null, -1);

		editor.addFile(filePath, null, -1);

		editor.applyTextDelta(filePath, null);

		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		String checksum = deltaGenerator.sendDelta(filePath,
				new ByteArrayInputStream(data), editor, true);

		editor.closeFile(filePath, checksum);

		// Closes dirPath.
		editor.closeDir();

		// Closes the root directory.
		editor.closeDir();

		return editor.closeEdit();
	}

	private static SVNCommitInfo addFile(ISVNEditor editor, String dirPath,
			String filePath, byte[] data) throws SVNException {

		editor.openRoot(-1);

		editor.openDir(dirPath, -1);

		editor.addFile(filePath, null, -1);

		editor.applyTextDelta(filePath, null);

		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		String checksum = deltaGenerator.sendDelta(filePath,
				new ByteArrayInputStream(data), editor, true);

		editor.closeFile(filePath, checksum);

		// Closes dirPath.
		editor.closeDir();

		// Closes the root directory.
		editor.closeDir();

		return editor.closeEdit();
	}
}
