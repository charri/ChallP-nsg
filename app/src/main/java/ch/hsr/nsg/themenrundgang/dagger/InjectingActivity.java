/*
 * Copyright (c) 2014 Fizz Buzz LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright (c) 2013 Fizz Buzz LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.hsr.nsg.themenrundgang.dagger;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import ch.hsr.nsg.themenrundgang.ViewModelModule;
import ch.hsr.nsg.themenrundgang.utils.OnListener;
import ch.hsr.nsg.themenrundgang.utils.OnManager;
import ch.hsr.nsg.themenrundgang.utils.OnObserver;
import dagger.ObjectGraph;

import static ch.hsr.nsg.themenrundgang.dagger.Preconditions.checkState;

/**
 * Manages an ObjectGraph on behalf of an Activity.  This graph is created by extending the application-scope graph with
 * Activity-specific module(s).
 */
public class InjectingActivity
        extends Activity
        implements Injector, OnObserver {
    private ObjectGraph mObjectGraph;
    private OnManager mOnManager;

    /**
     * Gets this Activity's object graph.
     *
     * @return the object graph
     */
    @Override
    public final ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }

    /**
     * Injects a target object using this Activity's object graph.
     *
     * @param target the target object
     */
    @Override
    public void inject(Object target) {
        checkState(mObjectGraph != null, "object graph must be assigned prior to calling inject");
        mObjectGraph.inject(target);
    }

    // implement Injector interface

    /**
     * Creates an object graph for this Activity by extending the application-scope object graph with the modules
     * returned by {@link #getModules()}.
     * <p/>
     * Injects this Activity using the created graph.
     */
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        // extend the application-scope object graph with the modules for this activity
        mObjectGraph = ((Injector) getApplication()).getObjectGraph().plus(getModules().toArray());
        mOnManager = new OnManager();
        // now we can inject ourselves
        inject(this);

        // note: we do the graph setup and injection before calling super.onCreate so that InjectingFragments
        // associated with this InjectingActivity can do their graph setup and injection in their
        // onAttach override.
        super.onCreate(savedInstanceState);

    }
    
    @Override public void setContentView(int layoutResID) {
    	super.setContentView(layoutResID);
    	ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        mObjectGraph = null;

        super.onDestroy();
    }

    /**
     * Returns the list of dagger modules to be included in this Activity's object graph.  Subclasses that override
     * this method should add to the list returned by super.getModules().
     *
     * @return the list of modules
     */
    protected List<Object> getModules() {
        List<Object> result = new ArrayList<Object>();
        result.add(new InjectingActivityModule(this, this));
        result.add(new ViewModelModule());
        return result;
    }

    @Override
    public void update(Observable observable, Object data) {
        mOnManager.update(observable, data);
    }
    @Override
    public void addOnListener(String key, OnListener listener) {
        mOnManager.addOnListener(key, listener);
    }
    @Override
    public boolean removeOnListener(String key, OnListener listener) {
        return mOnManager.removeOnListener(key, listener);
    }

}
