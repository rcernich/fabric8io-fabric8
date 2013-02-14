package org.fusesource.fabric.api;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author Stan Lewis
 */
public interface ProfileDataStore {

    Properties getProfileAttributes(String version, String id);

    void setProfileAttribute(String version, String id, String key, String value);

    Map<String, byte[]> getFileConfigurations(String version, String id);

    byte[] getFileConfiguration(String version, String id, String pid) throws InterruptedException, KeeperException;

    void setFileConfigurations(String version, String id, Map<String, byte[]> configurations);

    Map<String, Map<String, String>> getConfigurations(String version, String id);

    Map<String, String> getConfiguration(String version, String id, String pid) throws InterruptedException, KeeperException, IOException;

    void setConfigurations(String version, String id, Map<String, Map<String, String>> configurations);

    Properties getVersionAttributes(String version);

    void setVersionAttribute(String version, String key, String value);
}
