package br.com.lukinhasssss.proposta.clients.account

import br.com.lukinhasssss.proposta.clients.account.response.AccountResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "account", url = "\${clients.account.url}")
interface AccountClient {

    @GetMapping("/contas")
    fun getAllAccounts(): ResponseEntity<Set<AccountResponse>>

}