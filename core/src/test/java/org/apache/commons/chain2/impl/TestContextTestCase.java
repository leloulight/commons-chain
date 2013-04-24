/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.chain2.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.chain2.Context;
import org.junit.Before;
import org.junit.Test;


/**
 * Extension of <code>ContextBaseTestCase</code> to validate property
 * delegation.
 */

public class TestContextTestCase extends ContextBaseTestCase {


    // ---------------------------------------------------- Overall Test Methods


    /**
     * Set up instance variables required by this test case.
     */
    @Override
    @Before
    public void setUp() {
        context = createContext();
    }


    // ------------------------------------------------- Individual Test Methods


    // Test state of newly created instance
    @Override
    @Test
    public void testPristine() {

        super.testPristine();
        assertEquals("readOnly", context.get("readOnly"));
        assertEquals("readWrite", context.get("readWrite"));
        assertEquals("writeOnly", ((TestContext) context).returnWriteOnly());

    }


    // Test a read only property on the Context implementation class
    @Test
    public void testReadOnly() {

        Object readOnly = context.get("readOnly");
        assertNotNull("readOnly found", readOnly);
        assertTrue("readOnly String",
                   readOnly instanceof String);
        assertEquals("readOnly value", "readOnly", readOnly);

        try {
            context.put("readOnly", "new readOnly");
            fail("Should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Expected result
        }
        assertEquals("readOnly unchanged", "readOnly",
                     context.get("readOnly"));

    }


    // Test a read write property on the Context implementation class
    @Test
    public void testReadWrite() {

        Object readWrite = context.get("readWrite");
        assertNotNull("readWrite found", readWrite);
        assertTrue("readWrite String",
                   readWrite instanceof String);
        assertEquals("readWrite value", "readWrite", readWrite);

        context.put("readWrite", "new readWrite");
        readWrite = context.get("readWrite");
        assertNotNull("readWrite found", readWrite);
        assertTrue("readWrite String",
                   readWrite instanceof String);
        assertEquals("readWrite value", "new readWrite", readWrite);

    }


    // Test a write only property on the Context implementation class
    @Test
    public void testWriteOnly() {

        Object writeOnly = ((TestContext) context).returnWriteOnly();
        assertNotNull("writeOnly found", writeOnly);
        assertTrue("writeOnly String",
                   writeOnly instanceof String);
        assertEquals("writeOnly value", "writeOnly", writeOnly);

        context.put("writeOnly", "new writeOnly");
        writeOnly = ((TestContext) context).returnWriteOnly();
        assertNotNull("writeOnly found", writeOnly);
        assertTrue("writeOnly String",
                   writeOnly instanceof String);
        assertEquals("writeOnly value", "new writeOnly", writeOnly);

    }


    // ------------------------------------------------------- Protected Methods


    // Create a new instance of the appropriate Context type for this test case
    @Override
    protected Context<String, Object> createContext() {
        return (new TestContext());
    }


}
