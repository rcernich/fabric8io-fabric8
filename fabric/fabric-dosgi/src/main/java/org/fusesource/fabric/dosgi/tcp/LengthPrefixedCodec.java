/**
 * Copyright (C) 2011, FuseSource Corp.  All rights reserved.
 * http://fusesource.com
 *
 * The software in this package is published under the terms of the
 * CDDL license a copy of which has been included with this distribution
 * in the license.txt file.
 */

package org.fusesource.fabric.dosgi.tcp;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtdispatch.transport.AbstractProtocolCodec;

import java.io.IOException;
import java.net.ProtocolException;

public class LengthPrefixedCodec extends AbstractProtocolCodec {

    private final Action readLengthAction = new Action() {
        @Override
        public Object apply() throws IOException {
            Buffer buffer = readBytes(4);
            if( buffer != null ) {
                // Yeah.. we want to include the length header in the next buffer read.
                readStart -= 4;
                final int length = buffer.bigEndianEditor().readInt();
                if( length > maxMessageSize) {
                    throw new ProtocolException("Message size exceeds configured maximum");
                }
                nextDecodeAction = new Action() {
                    @Override
                    public Object apply() throws IOException {
                        Buffer b = readBytes(length);
                        if( b!=null ) {
                            nextDecodeAction = readLengthAction;
                            return b;
                        } else {
                            return null;
                        }
                    }
                };
                return nextDecodeAction.apply();
            } else {
                return null;
            }
        }
    };

    int maxMessageSize = 1024*1024*100;

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    @Override
    protected Action initialDecodeAction() {
        return readLengthAction;
    }
    
    @Override
    protected void encode(Object value) throws IOException {
        Buffer buffer = (Buffer) value;
        writeDirect(buffer.toByteBuffer());
    }


}
