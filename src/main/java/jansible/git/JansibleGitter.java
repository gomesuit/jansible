package jansible.git;

import jansible.file.JansibleFiler;
import jansible.model.common.ProjectKey;

import java.io.File;
import java.util.Iterator;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JansibleGitter {
	@Autowired
	private JansibleFiler jansibleFiler;
	
	public void cloneRepository(ProjectKey projectKey, String url) {
		String projectDirName = jansibleFiler.getProjectDirName(projectKey);
		cloneRepository(url, projectDirName);
	}
	
	private void cloneRepository(String url, String localPath) {
		CloneCommand cmd = Git.cloneRepository();
		cmd.setURI(url);
		cmd.setDirectory(new File(localPath));
		
		Git git;
		try {
			git = cmd.call();
			git.getRepository().close();
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commitAndPush(ProjectKey projectKey, String name, String pass, String comment){
		String projectDirName = jansibleFiler.getProjectDirName(projectKey);
		commitAndPush(projectDirName, name, pass, comment);
	}
	
	private void commitAndPush(String localPath, String name, String pass, String comment) {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		File gitDir = new File(localPath + "/.git");
		builder.setGitDir(gitDir);
		builder.readEnvironment();
		
		try(Git git = new Git(builder.build())){
			
	        AddCommand addCommand = git.add();
	        addCommand.addFilepattern(".");
	        addCommand.call();
			
	        CommitCommand commitCommand = git.commit();
	        commitCommand.setMessage(comment);
	        commitCommand.setAll(true);
	        commitCommand.call();
			
			CredentialsProvider cp = new UsernamePasswordCredentialsProvider(name, pass);
			
	        PushCommand pc = git.push();
	        pc.setCredentialsProvider(cp).setForce(true).setPushAll();

	        Iterator<PushResult> it = pc.call().iterator();
	        if(it.hasNext()){
	            System.out.println(it.next().toString());
	        }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
