package com.trade.billing.service

import com.trade.billing.model.Client
import com.trade.billing.repository.ClientRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ClientServiceTest {
    @InjectMocks
    lateinit var clientService: ClientService //clae que se va a probar

    @Mock   //objeto simulado
    lateinit var clientRepository: ClientRepository

    val clientMock = Client().apply {
        id=1
        nui="0301707030"
        fullName="Juan"
        address= "Ceunca"
        email= "JD@gmal.com"
    }

    @Test
    fun saveClientCorrect(){
        Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
        val response = clientService.save(clientMock)
        Assertions.assertEquals(response.id, clientMock.id)
    }


    @Test
    fun saveClientWhenFullnameIsBlank(){

        Assertions.assertThrows(Exception::class.java) {
            clientMock.apply { fullName=" "}
            Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
            clientService.save(clientMock)
        }

    }
    @Test
    fun saveClienWhenAddressIsBlanck(){
        Assertions.assertThrows(Exception::class.java) {
            clientMock.apply { address=" "}
            Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
            clientService.save(clientMock)
        }
    }
}