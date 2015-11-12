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
import org.eclipse.jgit.api.TagCommand;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.InvalidTagNameException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
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
	
	public void cloneRepository(ProjectKey projectKey, String url) throws Exception {
		String projectDirName = jansibleFiler.getProjectDirName(projectKey);
		cloneRepository(url, projectDirName);
	}
	
	private void cloneRepository(String url, String localPath) throws Exception {
		callClone(url, localPath);
	}
	
	public void commitAndPush(ProjectKey projectKey, String name, String pass, String comment) throws Exception{
		String projectDirName = jansibleFiler.getProjectDirName(projectKey);
		commitAndPush(projectDirName, name, pass, comment);
	}
	
	private void commitAndPush(String localPath, String name, String pass, String comment) throws Exception {
		File gitDir = getGitDir(localPath);
		FileRepositoryBuilder builder = createBuilder(gitDir);
		
		try(Git git = new Git(builder.build())){
			
	        callAdd(git);
	        
	        callCommit(git, comment);
			
	        callPush(git, getCredentialsProvider(name, pass));
	        
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	private FileRepositoryBuilder createBuilder(File gitDir){
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setGitDir(gitDir);
		builder.readEnvironment();
		return builder;
	}
	
	private CredentialsProvider getCredentialsProvider(String name, String pass){
		return new UsernamePasswordCredentialsProvider(name, pass);
	}
	
	public void tagAndPush(ProjectKey projectKey, String name, String pass, String tagName, String message) throws Exception{
		String projectDirName = jansibleFiler.getProjectDirName(projectKey);
		tagAndPush(projectDirName, name, pass, tagName, message);
	}
	
	private void tagAndPush(String localPath, String name, String pass, String tagName, String message) throws Exception {
		File gitDir = getGitDir(localPath);
		FileRepositoryBuilder builder = createBuilder(gitDir);
		
		try(Git git = new Git(builder.build())){
			callTag(git, tagName, message);
	        callPush(git, getCredentialsProvider(name, pass));
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	private void callTag(Git git, String tagName, String message) throws ConcurrentRefUpdateException, InvalidTagNameException, NoHeadException, GitAPIException{
        TagCommand tagCommand = git.tag();
        tagCommand.setName(tagName);
        tagCommand.setMessage(message);
        tagCommand.call();
	}
	
	private void callPush(Git git, CredentialsProvider cp) throws InvalidRemoteException, TransportException, GitAPIException{
        PushCommand pushCommand = git.push();
        pushCommand.setCredentialsProvider(cp);
        pushCommand.setForce(true);
        pushCommand.setPushAll();
        pushCommand.setPushTags();
        
        Iterator<PushResult> it = pushCommand.call().iterator();
        if(it.hasNext()){
            System.out.println(it.next().toString());
        }
	}
	
	private void callAdd(Git git) throws NoFilepatternException, GitAPIException{
        AddCommand addCommand = git.add();
        addCommand.addFilepattern(".");
        addCommand.call();
	}
	
	private void callCommit(Git git, String comment) throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, AbortedByHookException, GitAPIException{
        CommitCommand commitCommand = git.commit();
        commitCommand.setMessage(comment);
        commitCommand.setAll(true);
        commitCommand.call();
	}
	
	private void callClone(String url, String localPath) throws InvalidRemoteException, TransportException, GitAPIException{
		CloneCommand cmd = Git.cloneRepository();
		cmd.setURI(url);
		cmd.setDirectory(new File(localPath));
		cmd.call();
	}

	private File getGitDir(String localPath){
		return new File(getGitDirName(localPath));
	}
	
	private String getGitDirName(String localPath){
		return localPath + "/.git";
	}
}
