package com.dellpoc.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TestListener implements TestWatcher {
    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info("Test passed: " + context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.error("Test failed: " + context.getDisplayName(), cause);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        log.warn("Test aborted: " + context.getDisplayName());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        log.info("Test disabled: " + context.getDisplayName() + " Reason: " + reason.orElse("No reason"));
    }
}
