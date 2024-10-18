package com.github.titainper.codi8.services.brands.domain.event

import org.springframework.context.ApplicationEvent

class BrandDeletedEvent(id: Long) : ApplicationEvent(id)