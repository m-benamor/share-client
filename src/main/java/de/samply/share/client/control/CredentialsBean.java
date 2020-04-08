/*
 * Copyright (c) 2017 Medical Informatics Group (MIG),
 * Universitätsklinikum Frankfurt
 *
 * Contact: www.mig-frankfurt.de
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 *
 * If you modify this Program, or any covered work, by linking or combining it
 * with Jersey (https://jersey.java.net) (or a modified version of that
 * library), containing parts covered by the terms of the General Public
 * License, version 2.0, the licensors of this Program grant you additional
 * permission to convey the resulting work.
 */

package de.samply.share.client.control;

import de.samply.share.client.model.db.enums.AuthSchemeType;
import de.samply.share.client.model.db.enums.TargetType;
import de.samply.share.client.model.db.tables.pojos.Credentials;
import de.samply.share.client.util.connector.StoreConnector;
import de.samply.share.client.util.db.CredentialsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A ViewScoped backing bean used on the credentials page
 */
@ManagedBean(name = "credentialsBean")
@ViewScoped
public class CredentialsBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(CredentialsBean.class);

    /** The credentials list. */
    private List<Credentials> credentialsList;

    @ManagedProperty(value = "#{configurationBean}")
    private ConfigurationBean configurationBean;

    private String newUsername;
    private String newPassword;
    private String newWorkstation;
    private String newDomain;
    private AuthSchemeType newAuthSchemeType;
    private TargetType newTargetType;

    public String getBase64Credentials() {
        return StoreConnector.getBase64Credentials(StoreConnector.authorizedUsername, StoreConnector.authorizedPassword);
    }

    public List<Credentials> getCredentialsList() {
        return credentialsList;
    }

    public void setCredentialsList(List<Credentials> credentialsList) {
        this.credentialsList = credentialsList;
    }

    public ConfigurationBean getConfigurationBean() {
        return configurationBean;
    }

    public void setConfigurationBean(ConfigurationBean configurationBean) {
        this.configurationBean = configurationBean;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewWorkstation() {
        return newWorkstation;
    }

    public void setNewWorkstation(String newWorkstation) {
        this.newWorkstation = newWorkstation;
    }

    public String getNewDomain() {
        return newDomain;
    }

    public void setNewDomain(String newDomain) {
        this.newDomain = newDomain;
    }

    public AuthSchemeType getNewAuthSchemeType() {
        return newAuthSchemeType;
    }

    public void setNewAuthSchemeType(AuthSchemeType newAuthSchemeType) {
        this.newAuthSchemeType = newAuthSchemeType;
    }

    public TargetType getNewTargetType() {
        return newTargetType;
    }

    public void setNewTargetType(TargetType newTargetType) {
        this.newTargetType = newTargetType;
    }

    @PostConstruct
    public void init() {
        refreshCredentials();
        newTargetType = TargetType.TT_CENTRALSEARCH;
    }

    /**
     * Refresh credentials list from Database.
     */
    public void refreshCredentials() {
        credentialsList = CredentialsUtil.fetchCredentials();
    }


    /**
     * Get all defined Auth Schemes
     *
     * Definition is done via an ENUM in the database
     *
     * @return an array of defined auth schemes
     */
    public AuthSchemeType[] getAuthSchemeTypes() {
        // TODO: Currently, SPNEGO and Kerberos are unsupported! Remove the filter here when they are (delete everything and uncomment the last line)
        AuthSchemeType[] authSchemeValues = AuthSchemeType.values();
        List<AuthSchemeType> credentialsList = new ArrayList<>();
        for (AuthSchemeType ast : authSchemeValues) {
            if (ast != AuthSchemeType.AS_KERBEROS && ast != AuthSchemeType.AS_SPNEGO && ast != AuthSchemeType.AS_APIKEY) {
                credentialsList.add(ast);
            }
        }

        return credentialsList.toArray(new AuthSchemeType[0]);
    }

    /**
     * Get an array of supported Auth Schemes for the given target type
     *
     * @param target what is the target of the authentication
     * @return an array of applicable auth schemes for the target
     */
    public AuthSchemeType[] getAuthSchemeTypes(TargetType target) {
        logger.debug("get auth scheme types called for target " + target);
        if (target == null) {
            return getAuthSchemeTypes();
        }
        switch (target) {
            case TT_LDM:
            case TT_CENTRALSEARCH:
            case TT_BROKER:
                AuthSchemeType[] authSchemeValues = new AuthSchemeType[1];
                authSchemeValues[0] = AuthSchemeType.AS_BASIC;
                return authSchemeValues;
            case TT_HTTP_PROXY:
            case TT_HTTPS_PROXY:
            default:
                return getAuthSchemeTypes();
        }
    }

    /**
     * Get an array of (database-) defined target types
     *
     * @return array of target types
     */
    public ArrayList<TargetType> getTargetTypes() {
        ArrayList<TargetType> targetTypes= new ArrayList<>();
        targetTypes.add(TargetType.valueOf("TT_HTTP_PROXY"));
        targetTypes.add(TargetType.valueOf("TT_HTTPS_PROXY"));
        targetTypes.add(TargetType.valueOf("TT_LDM"));
        targetTypes.add(TargetType.valueOf("TT_BROKER"));
        targetTypes.add(TargetType.valueOf("TT_DIRECTORY"));
        if (ApplicationUtils.isDktk()) {
            targetTypes.add(TargetType.valueOf("TT_CENTRALSEARCH"));
        }
        return targetTypes;
    }

    /**
     * Delete credentials from database.
     *
     * @param credentials
     *            the credentials to delete
     * @return the outcome for the jsf2 navigation
     */
    public String deleteCredentials(Credentials credentials) {
        CredentialsUtil.deleteCredentials(credentials);
        ApplicationBean.initLdmConnector();
        return "credentials_list?faces-redirect=true";
    }

    /**
     * Add a new set of credentials and refresh the credentialsprovider
     */
    public void addCredentials() {
        Credentials newCredentials = new Credentials();
        newCredentials.setDomain(newDomain);
        newCredentials.setPasscode(newPassword);
        newCredentials.setTarget(newTargetType);
        if (newAuthSchemeType == null) {
            newCredentials.setAuthScheme(AuthSchemeType.AS_BASIC);
        } else {
            newCredentials.setAuthScheme(newAuthSchemeType);
        }
        newCredentials.setUsername(newUsername);
        newCredentials.setWorkstation(newWorkstation);
        CredentialsUtil.insertCredentials(newCredentials);
        clearNewCredentials();
        refreshCredentials();
        ApplicationBean.initLdmConnector();
    }

    /**
     * Clear the Form
     */
    private void clearNewCredentials() {
        setNewDomain(null);
        setNewPassword(null);
        setNewTargetType(TargetType.TT_CENTRALSEARCH);
        setNewAuthSchemeType(AuthSchemeType.AS_BASIC);
        setNewUsername(null);
        setNewWorkstation(null);
    }
}
