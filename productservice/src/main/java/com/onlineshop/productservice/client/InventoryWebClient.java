package com.onlineshop.productservice.client;

import com.onlineshop.productservice.client.util.ClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

/**
 * Raktárkészletet nyilvántartó mikroszolgáltatás elérését biztosító osztály.
 * A mikroszervizek egymás közötti biztonságos kommunikációja érdekében továbbítja a JWT tokent a user felöl.
 * Ez csak példa inkább, mert publikus oldalnál nem lesz token, csak admin felület esetén.
 */
@Component
@RequiredArgsConstructor
public class InventoryWebClient {

    private static final String INVENTORY_URL = "http://inventory-service/api/inventory/";
    private static final String BEARER = "Bearer ";
    private ClientUtil clientUtil;

    private final WebClient.Builder webClientBuilder;
    private final ServiceTokenProvider tokenProvider;

    /**
     * Raktárkészletet (inventory) nyilvántartó mikroszolgáltatás eléréséhez szükséges metódus.
     * A header részben továbbítja a felhasználótól érkező tokent.
     * Jelen esetben ez a megoldás nem használható, mert a felhasználó hozzáfér publikus oldalakhoz ahová nem kell token,
     * viszont a mikroszolgáltatásoknak ettől még biztonságosan kell kommunikálniuk egymással.
     * Erre a machine-to-machine hitelesítés kell.
     *
     * @param exchange
     * @param productId
     * @return
     */
//    public Mono<InventoryResponse> getInventory(ServerWebExchange exchange, String productId) {
//        return clientUtil.extractToken(exchange)
//                .flatMap(token -> webClientBuilder.build()
//                        .get()
//                        .uri(INVENTORY_URL + productId)
//                        .header(HttpHeaders.AUTHORIZATION, BEARER + token)
//                        .retrieve()
//                        .bodyToMono(InventoryResponse.class)
//                );
//    }

    /**
     * Minta hívás a machine-to-machine token használatával,
     * valamint minta az ottani token validáláshoz :)
     */
//    public Mono<InventoryResponse> getInventory(String key){ //, String productId) {
//        return tokenProvider.getServiceToken()
//                .flatMap(token -> webClientBuilder.build()
//                        .get()
//                        .uri(INVENTORY_URL + key)
//                        .header(HttpHeaders.AUTHORIZATION, BEARER + token)
//                        .retrieve()
//                        .bodyToMono(InventoryResponse.class)
//                );
//        /**
//         * A hívást az Inventory microservice így validálja a security configban:
//         * .oauth2ResourceServer(oauth2 -> oauth2
//         *    .jwt(jwt -> jwt
//         *        .jwkSetUri(http://aut-service_elérése/oaut2/jwks)
//         *    )
//         * )
//           *
//           * valamint az .auhorizeExchange(exchange -> exchange -hez hozzá kell adni:
//           * .pathMatchers("/api/productservice/**").hasAuthority("SCOPE_internal", "SCOPE_locale.read) //vágy más scope-t :)
//           *
//         * */
//    }
}
