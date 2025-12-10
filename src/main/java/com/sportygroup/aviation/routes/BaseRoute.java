package com.sportygroup.aviation.routes;

import com.sportygroup.aviation.enums.ApiRoutesEnum;
import com.sportygroup.aviation.processor.ExceptionProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.rest.RestDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static com.sportygroup.aviation.utils.Constants.DOC_V1_BASE_PATH;

@Component
public class BaseRoute extends RouteBuilder {

    @Autowired
    private ExceptionProcessor exceptionProcessor;

    @Override
    public void configure() throws Exception {

        onException(HttpOperationFailedException.class)
                .log(LoggingLevel.INFO, "HttpOperationFailedException Error processing ")
                .handled(true)
                .stop()
                .end();

        onException(Exception.class)
                .log(LoggingLevel.INFO, "Exception Error processing ")
                .handled(true)
                .process(exceptionProcessor)
                .stop()
                .end();

    }

    protected RestDefinition getPostDefinition(ApiRoutesEnum apisEnum, Class<?> requestType, Class<?> responseType) {
        return rest(DOC_V1_BASE_PATH)
                .id(apisEnum.getId())
                .description(apisEnum.getDescription())
                .post(apisEnum.getUrl())
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(requestType)
                .outType(responseType)
                .responseMessage()
                .code(HttpStatus.OK.value())
                .responseModel(String.class)
                .message(HttpStatus.OK.getReasonPhrase())
                .endResponseMessage()
                .to(apisEnum.getToRouteFrom());
    }
}
