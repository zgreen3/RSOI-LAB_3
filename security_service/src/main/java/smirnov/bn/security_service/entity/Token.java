package smirnov.bn.security_service.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(name = "access_token_uuid", unique = true)
    private UUID accessTokenUuid;

    @Column(name = "is_invalidated", columnDefinition = "boolean default false", nullable = false)
    private Boolean isInvalidated;

    @Column(name = "is_expired", columnDefinition = "boolean default false", nullable = false)
    private Boolean isExpired;

    @Column(name = "token_type", length = 255)
    private String tokenType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date_time")
    private Date createdDateTime;

    @Column(name="expires_in_seconds", columnDefinition = "BIGINT")
    private Long expiresInSeconds;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_used_date_time")
    private Date lastUsedDateTime;

    @Column(name = "client_id", length = 255)
    private String clientID;

    @Column(name = "refresh_token_uuid", unique = true)
    private UUID refreshTokenUuid;

    public Token(Boolean isInvalidated, Boolean isExpired, String tokenType, Date createdDateTime, Long expiresInSeconds, Date lastUsedDateTime, String clientID) {
        this.isInvalidated = isInvalidated;
        this.isExpired = isExpired;
        this.tokenType = tokenType;
        this.createdDateTime = createdDateTime;
        this.expiresInSeconds = expiresInSeconds;
        this.lastUsedDateTime = lastUsedDateTime;
        this.clientID = clientID;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public UUID getAccessTokenUuid() {
        return accessTokenUuid;
    }

    public void setAccessTokenUuid(UUID accessTokenUuid) {
        this.accessTokenUuid = accessTokenUuid;
    }

    public Boolean getInvalidated() {
        return isInvalidated;
    }

    public void setInvalidated(Boolean invalidated) {
        isInvalidated = invalidated;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(Long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public Date getLastUsedDateTime() {
        return lastUsedDateTime;
    }

    public void setLastUsedDateTime(Date lastUsedDateTime) {
        this.lastUsedDateTime = lastUsedDateTime;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public UUID getRefreshTokenUuid() {
        return refreshTokenUuid;
    }

    public void setRefreshTokenUuid(UUID refreshTokenUuid) {
        this.refreshTokenUuid = refreshTokenUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(tokenId, token.tokenId) &&
                Objects.equals(accessTokenUuid, token.accessTokenUuid) &&
                Objects.equals(isInvalidated, token.isInvalidated) &&
                Objects.equals(isExpired, token.isExpired) &&
                Objects.equals(tokenType, token.tokenType) &&
                Objects.equals(createdDateTime, token.createdDateTime) &&
                Objects.equals(expiresInSeconds, token.expiresInSeconds) &&
                Objects.equals(lastUsedDateTime, token.lastUsedDateTime) &&
                Objects.equals(clientID, token.clientID) &&
                Objects.equals(refreshTokenUuid, token.refreshTokenUuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tokenId, accessTokenUuid, isInvalidated, isExpired, tokenType, createdDateTime, expiresInSeconds, lastUsedDateTime, clientID, refreshTokenUuid);
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenId=" + tokenId +
                ", accessTokenUuid=" + accessTokenUuid +
                ", isInvalidated=" + isInvalidated +
                ", isExpired=" + isExpired +
                ", tokenType='" + tokenType + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", expiresInSeconds=" + expiresInSeconds +
                ", lastUsedDateTime=" + lastUsedDateTime +
                ", clientID='" + clientID + '\'' +
                ", refreshTokenUuid=" + refreshTokenUuid +
                '}';
    }
}
