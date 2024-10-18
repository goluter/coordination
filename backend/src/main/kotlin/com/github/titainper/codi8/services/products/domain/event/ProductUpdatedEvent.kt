package com.github.titainper.codi8.services.products.domain.event

import org.springframework.context.ApplicationEvent

class ProductUpdatedEvent(id: Long) : ApplicationEvent(id)