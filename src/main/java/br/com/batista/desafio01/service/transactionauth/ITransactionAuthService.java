package br.com.batista.desafio01.service.transactionauth;

import br.com.batista.desafio01.model.dto.AuthorizeDTO;
import reactor.core.publisher.Mono;

public interface ITransactionAuthService {
    Mono<AuthorizeDTO> check(String param);
}
