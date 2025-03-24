package br.com.batista.desafio01.service.transactionauth;

import br.com.batista.desafio01.model.dto.AuthorizeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class TransactionAuthService implements ITransactionAuthService{

    private final WebClient webClient;

    public TransactionAuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://util.devi.tools").build();
    }

    @Override
    public Mono<AuthorizeDTO> check(String param) {
        return webClient.get()
                .uri("/api/v2/authorize", param)
                .retrieve()
                .bodyToMono(AuthorizeDTO.class);
    }
}
