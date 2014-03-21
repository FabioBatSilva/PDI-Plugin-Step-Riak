package com.instaclick.pentaho.plugin.riak.processor;

import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.operations.FetchObject;
import com.instaclick.pentaho.plugin.riak.AggregateSiblingsResolver;
import com.instaclick.pentaho.plugin.riak.RiakPlugin;
import com.instaclick.pentaho.plugin.riak.RiakPluginData;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.pentaho.di.core.row.RowMetaInterface;

public class GetProcessorTest
{
    Bucket bucket;
    RiakPlugin plugin;
    RiakPluginData data;

    @Before
    public void setUp()
    {
        plugin = mock(RiakPlugin.class);
        data   = mock(RiakPluginData.class);
        bucket = mock(Bucket.class, RETURNS_MOCKS);
    }

    @Test
    public void testProcessInvalidKey() throws Exception
    {
        final String value           = "foo";
        final String key             = "bar";
        final Object[] row           = new Object[] {key, value};
        final RowMetaInterface meta  = mock(RowMetaInterface.class);
        final GetProcessor processor = new GetProcessor(bucket, plugin, data);

        data.keyFieldIndex = 2;
        data.outputRowMeta = meta;

        assertFalse(processor.process(row));
        verify(bucket, never()).delete(eq(key));
    }
}