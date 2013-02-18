package org.fusesource.fabric.git.http;

import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.fabric.groups.NodeState;

public class GitNode implements NodeState {
	@JsonProperty
	String id;


	@JsonProperty
	String url;

	/**
	 * The id of the cluster node.  There can be multiple node with this ID,
	 * but only the first node in the cluster will be the master for for it.
	 */
	@Override
	public String id() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "GitNode{" +
				"id='" + id + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
