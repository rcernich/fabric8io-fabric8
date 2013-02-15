package org.fusesource.fabric.api;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;
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

    void createVersion(String version);

    void createVersion(String parentVersionId, String toVersion);

    void deleteVersion(String version);

    List<String> getVersions();

    String getVersion(String name);

    List<String> getProfiles(String version);

    String getProfile(String version, String name);

    String createProfile(String version, String name);

    void deleteProfile(String versionId, String profileId);

    void importFromFileSystem(String from);

    String getDefaultVersion();

    void setDefaultVersion(String versionId);

    void setFileConfiguration(String version, String id, String pid, byte[] configuration);

    String getProfile(String version, String name, boolean create);

    void setConfiguration(String version, String id, String pid, Map<String, String> configuration);
}
