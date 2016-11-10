package io.b1ackr0se.bridddle.base;

class ViewNotAttachedException extends RuntimeException {
    ViewNotAttachedException() {
        super("Please call attachView() before proceeding!");
    }
}
