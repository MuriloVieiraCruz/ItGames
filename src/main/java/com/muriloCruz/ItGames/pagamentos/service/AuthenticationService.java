//package com.muriloCruz.ItGames.pagamentos.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.muriloCruz.ItGames.entity.Usuario;
//import com.muriloCruz.ItGames.pagamentos.model.AuthenticationToken;
//import com.muriloCruz.ItGames.pagamentos.repository.TokenRepository;
//import com.muriloCruz.ItGames.pagamentos.util.Helper;
//import com.muriloCruz.ItGames.pagamentos.util.MessageStrings;
//
//
//@Service
//public class AuthenticationService {
//
//	 @Autowired
//	 private TokenRepository repository;
//
//	    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
//	        repository.save(authenticationToken);
//	    }
//
//	    public AuthenticationToken getToken(Usuario user) {
//	        return repository.findTokenByUser(user);
//	    }
//
//	    public Usuario getUser(String token) {
//	        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
//	        if (Helper.notNull(authenticationToken)) {
//	            if (Helper.notNull(authenticationToken.getUser())) {
//	                return authenticationToken.getUser();
//	            }
//	        }
//	        return null;
//	    }
//
//	    public void authenticate(String token) throws Exception {
//	        if (!Helper.notNull(token)) {
//	            throw new RuntimeException(MessageStrings.AUTH_TOEKN_NOT_PRESENT);
//	        }
//	        if (!Helper.notNull(getUser(token))) {
//	            throw new RuntimeException(MessageStrings.AUTH_TOEKN_NOT_VALID);
//	        }
//	    }
//}
