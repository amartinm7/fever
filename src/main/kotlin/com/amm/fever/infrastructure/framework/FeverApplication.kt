package com.amm.fever.infrastructure.framework

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
	scanBasePackages = [
		"com.amm.fever.infrastructure.framework",
	],
)
class FeverApplication

fun main(args: Array<String>) {
	runApplication<FeverApplication>(*args)
}
