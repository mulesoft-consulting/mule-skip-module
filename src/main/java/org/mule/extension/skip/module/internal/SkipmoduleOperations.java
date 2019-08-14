package org.mule.extension.skip.module.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.mule.runtime.extension.api.runtime.route.Chain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */

public class SkipmoduleOperations {

    private static final Logger log = LoggerFactory.getLogger("org.mule.extension.skipModule");

    public void skip(@Optional(defaultValue = "true") @Expression(ExpressionSupport.NOT_SUPPORTED) boolean shouldSkip,
                     ComponentLocation location,
                     Chain operations,
                     CompletionCallback<Object, Object> callback) {

        if (!shouldSkip) {
            operations.process(
                    result -> {
                        System.out.println(result.getOutput());
                        callback.success(result);
                    },
                    (error, previous) -> {
                        System.out.println(error.getMessage());
                        callback.error(error);
                    });
        } else {
            log.info("Skipping processors at file: " + location.getFileName().orElse("") + " line: " + String.valueOf(location.getLineInFile().orElse(null)));
            callback.success(null);
        }
    }
}
