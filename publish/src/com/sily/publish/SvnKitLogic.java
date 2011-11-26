package com.sily.publish;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SvnKitLogic {
	
	
	public static ISVNEditor init(){
		FSRepositoryFactory.setup();
		String url = "https://silyproject.googlecode.com/svn/trunk/";
		ISVNEditor editor = null;
		SVNRepository repository;
		try {
			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIDecoded(url));

			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager("jishijun204@163.com",
							"Ce3sy3xB5Gc3");

			repository.setAuthenticationManager(authManager);

			String logMessage = "svnkit test log ";
			editor = repository.getCommitEditor(logMessage,
					null /* locks */, true /* keepLocks */, null /* mediator */);

		} catch (SVNException e) {
			e.printStackTrace();
		}
		
		return editor;
	}

	public static byte[] getBytesFromFile(File f) {
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
	
	public static String process(File f,String svnPath){
		ISVNEditor editor = init();
		if(editor == null){
			return "";
		}
		SVNCommitInfo svnCommitInfo;
		try {
			svnCommitInfo = addDir(
					editor,
					svnPath,
					f.getName(),
					getBytesFromFile(f));
			System.out.println(svnCommitInfo.getNewRevision());
			
			System.out.println(svnCommitInfo.getAuthor());
			
			return "https://silyproject.googlecode.com/svn/trunk/" + svnPath + "/" + f.getName();
		} catch (SVNException e) {
			e.printStackTrace();
		}
		return "";
	}
}
