package org.fusesource.fabric.git;

import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public interface FabricGitService {

	int DEFAULT_TIMEOUT = 10000;
	String DEFAULT_LOCAL_LOCATION = System.getProperty("karaf.data") + File.separator + "git" + File.separator + "local";

	Git get() throws IOException;
}
