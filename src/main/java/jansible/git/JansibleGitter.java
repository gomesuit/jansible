package jansible.git;

import java.io.File;
import java.util.Iterator;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.api.TagCommand;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.InvalidTagNameException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

@Service
public class JansibleGitter {
	
	public void addSubmodule(String localPath, String uri, String path) throws Exception{
		File gitDir = getGitDir(localPath);
		FileRepositoryBuilder builder = createBuilder(gitDir);
		
		try(Git git = new Git(builder.build())){
			callSubmodule(git, uri, path);
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public void commitAndPush(String localPath, String name, String pass, String comment) throws Exception {
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
	
	public void tagAndPush(String dirName, GitOperationInfo gitOperationInfo, String tagName) throws Exception{
		tagAndPush(dirName, gitOperationInfo.getUserName(), gitOperationInfo.getPassword(), tagName, gitOperationInfo.getComment());
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
	
	public void initAndUpdateSubmodule(String localPath) throws Exception{
		File gitDir = getGitDir(localPath);
		FileRepositoryBuilder builder = createBuilder(gitDir);
		
		try(Git git = new Git(builder.build())){
			git.submoduleInit().call();
			git.submoduleUpdate().call();
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
	
	public void callClone(String url, String localPath, GitCredentialInfo info) throws InvalidRemoteException, TransportException, GitAPIException{
		callClone(url, localPath, info.getUserName(), info.getPassword());
	}
	
	private void callClone(String url, String localPath, String name, String pass) throws InvalidRemoteException, TransportException, GitAPIException{
		CloneCommand cmd = Git.cloneRepository();
		cmd.setURI(url);
		cmd.setDirectory(new File(localPath));
		cmd.setCredentialsProvider(getCredentialsProvider(name, pass));
		try(Git git = cmd.call()){}
	}

	private File getGitDir(String localPath){
		return new File(getGitDirName(localPath));
	}
	
	private String getGitDirName(String localPath){
		return localPath + "/.git";
	}
	
	private void callSubmodule(Git git, String uri, String path) throws GitAPIException {
		SubmoduleAddCommand command = git.submoduleAdd();
		command.setURI(uri);
		command.setPath(path);
		command.call();
	}
	
	public void checkoutSubmodule(String localPath, String submodulePath, String tagName) throws Exception {
		File gitDir = getGitDir(localPath);
		FileRepositoryBuilder builder = createBuilder(gitDir);
		Repository repository = builder.build();
		Repository subRepo = SubmoduleWalk.getSubmoduleRepository(repository, submodulePath);
		
		try(Git git = new Git(subRepo)){
			callFetch(git);
			callCheckout(git, tagName);
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	private void callCheckout(Git git, String tagName) throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException{
		CheckoutCommand command = git.checkout();
		command.setName(tagName);
		command.call();
	}
	
	private void callFetch(Git git) throws WrongRepositoryStateException, InvalidConfigurationException, DetachedHeadException, InvalidRemoteException, CanceledException, RefNotFoundException, RefNotAdvertisedException, NoHeadException, TransportException, GitAPIException{
		FetchCommand command = git.fetch();
		command.call();
	}
}
