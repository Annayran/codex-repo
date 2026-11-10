package br.com.zaya.apiboletos.services;

import java.util.*;

public final class ApiRequest {
  private String id, configuration, credentials, command, profile;
  private long company, userId, nuFin;
  private Map<String, String> values = new LinkedHashMap<String, String>();

  public String getId() {
    return id;
  }

  public void setId(String v) {
    id = v;
  }

  public String getConfiguration() {
    return configuration;
  }

  public void setConfiguration(String v) {
    configuration = v;
  }

  public String getCredentials() {
    return credentials;
  }

  public void setCredentials(String v) {
    credentials = v;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String v) {
    command = v;
  }

  public long getCompany() {
    return company;
  }

  public void setCompany(long v) {
    company = v;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long v) {
    userId = v;
  }

  public long getNuFin() {
    return nuFin;
  }

  public void setNuFin(long v) {
    nuFin = v;
  }

  public String getProfile() {
    return profile;
  }

  public void setProfile(String v) {
    profile = v;
  }

  public Map<String, String> getValues() {
    return values;
  }

  public void setValues(Map<String, String> v) {
    values = v;
  }
}
