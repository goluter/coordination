package com.github.titainper.codi8.services.products.domain.event

import org.springframework.context.ApplicationEvent

class ProductCreatedEvent(id: Long) : ApplicationEvent(id)