package grailsactuatortest


import grails.rest.*
import grails.converters.*
import org.apache.commons.lang3.ThreadUtils

class AccountController {
    static responseFormats = ['json', 'xml']

    def index() {
        def name = params.name ?: "world"
        int rand = Random.newInstance().nextInt(2000)
        log.info("sleep $rand)")
        ThreadUtils.sleep(rand)
        render "Welcome $name"
    }
}
