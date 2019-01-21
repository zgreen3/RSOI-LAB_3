package smirnov.bn.security_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import smirnov.bn.security_service.model.AuthorizationCodeInfo;
import smirnov.bn.security_service.model.TokenInfo;
import smirnov.bn.security_service.service.AuthAndTokenServiceImpl;

import java.util.UUID;

@RestController //@Controller
@RequestMapping("/security_service/authorization")
public class OAuth2CustomController {

    @Autowired
    private AuthAndTokenServiceImpl authAndTokenService; //= new AuthAndTokenServiceImpl();

    private static final Logger logger = LoggerFactory.getLogger(OAuth2CustomController.class);

    @RequestMapping(value = {"/create-auth-code"}, method = RequestMethod.POST)
    @ResponseBody
    public String createAuthCode(@RequestBody AuthorizationCodeInfo authorizationCodeInfo) {
        try {
            UUID newAuthCodeUuid = authAndTokenService.createAuthenticationCode(authorizationCodeInfo.getClientID(), authorizationCodeInfo.getRedirectionURI());
            logger.info("/security_service/authorization : /create-auth-code, createAuthCode() - CREATING" + "\n" + "uuid param: " + String.valueOf(newAuthCodeUuid));
            return newAuthCodeUuid.toString();
        } catch (Exception e) {
            logger.error("Error in createAuthCode(...)", e);
            return null;
        }
    }

    @RequestMapping(value = {"/create-access-token"}, method = RequestMethod.POST)
    @ResponseBody
    public String createAccessToken(@RequestBody TokenInfo tokenInfo) {
        try {
            UUID newAccessTokenUuid = authAndTokenService.createAccessToken(tokenInfo.getClientID());
            logger.info("/security_service/authorization : /create-access-token, createAccessToken() - CREATING" + "\n" + "uuid param: " + String.valueOf(newAccessTokenUuid));
            return newAccessTokenUuid.toString();
        } catch (Exception e) {
            logger.error("Error in createAccessToken(...)", e);
            return null;
        }
    }

}
