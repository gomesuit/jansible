package sample;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class Main {

	public static void main(String[] args) throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		//cloneSample();
		commitSample();
		pushSample();
	}
	
	private static void pushSample() throws IOException, NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, AbortedByHookException, GitAPIException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File("D:/temp/git/.git")).readEnvironment().findGitDir().build();
		
		Git git = new Git(repository);
		
		
		
		//RefSpec spec = new RefSpec(branch + ":" + branch);
		//git.push().call();
		
		CredentialsProvider cp = new UsernamePasswordCredentialsProvider("name", "password");
		
        PushCommand pc = git.push();
        pc.setCredentialsProvider(cp).setForce(true).setPushAll();
		

        Iterator<PushResult> it = pc.call().iterator();
        if(it.hasNext()){
            System.out.println(it.next().toString());
        }
		
        git.close();
	}
	
	private static void commitSample() throws IOException, NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, AbortedByHookException, GitAPIException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File("D:/temp/git/.git")).readEnvironment().findGitDir().build();
		
		Git git = new Git(repository);
		

        AddCommand ac = git.add();
        ac.addFilepattern(".").call();
		
		git.commit().setMessage("コミット内容").call();
		
        git.close();
	}

	private static void cloneSample() throws InvalidRemoteException, TransportException, GitAPIException{
		CloneCommand cmd = Git.cloneRepository();
		cmd.setURI("https://github.com/gomesuit/gittest.git");
		cmd.setDirectory(new File("D:/temp/git"));
		cmd.setBare(false);
		
		Git git = cmd.call();
		
		//git.pull().call();

        git.getRepository().close();
	}

}
