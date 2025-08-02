package br.com.alfser.projeto.processa_pagamentos

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.with

@SpringBootTest(classes = [ProcessaPagamentosApplication::class])
class ProcessaPagamentosApplicationTests

fun main(args: Array<String>) {
	fromApplication<ProcessaPagamentosApplication>().with(TestcontainersConfiguration::class).run(*args)
}