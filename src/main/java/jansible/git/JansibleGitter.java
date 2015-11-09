package jansible.git;

import jansible.file.JansibleFiler;
import jansible.model.common.ProjectKey;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
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
	
	public void commitAndPush(String localPath, String name, String pass, String comment) throws InvalidRemoteException, TransportException, GitAPIException, IOException{
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File(localPath)).readEnvironment().findGitDir().build();

		Git git = new Git(repository);
		
        AddCommand ac = git.add();
        ac.addFilepattern(".").call();
		
		git.commit().setMessage(comment).call();
		
		
		CredentialsProvider cp = new UsernamePasswordCredentialsProvider(name, pass);
		
        PushCommand pc = git.push();
        pc.setCredentialsProvider(cp).setForce(true).setPushAll();

        Iterator<PushResult> it = pc.call().iterator();
        if(it.hasNext()){
            System.out.println(it.next().toString());
        }
		
        git.close();
	}
}
