/*
 * Copyright 2011 Azwan Adli Abdullah
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gh4a.fragment;

import java.util.List;

import org.eclipse.egit.github.core.Milestone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;

import com.gh4a.Constants;
import com.gh4a.R;
import com.gh4a.activities.IssueMilestoneEditActivity;
import com.gh4a.adapter.MilestoneAdapter;
import com.gh4a.adapter.RootAdapter;
import com.gh4a.loader.LoaderResult;
import com.gh4a.loader.MilestoneListLoader;

public class IssueMilestoneListFragment extends ListDataBaseFragment<Milestone> {
    private String mRepoOwner;
    private String mRepoName;
    private String mState;

    public static IssueMilestoneListFragment newInstance(String repoOwner, String repoName, String state) {
        IssueMilestoneListFragment f = new IssueMilestoneListFragment();

        Bundle args = new Bundle();
        args.putString(Constants.Repository.OWNER, repoOwner);
        args.putString(Constants.Repository.NAME, repoName);
        args.putString(Constants.Milestone.STATE, state);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepoOwner = getArguments().getString(Constants.Repository.OWNER);
        mRepoName = getArguments().getString(Constants.Repository.NAME);
        mState = getArguments().getString(Constants.Milestone.STATE);
    }

    @Override
    protected RootAdapter<Milestone> onCreateAdapter() {
        return new MilestoneAdapter(getActivity());
    }

    @Override
    protected int getEmptyTextResId() {
        return R.string.no_milestones_found;
    }

    @Override
    protected void onItemClick(Milestone milestone) {
        Intent intent = new Intent(getActivity(), IssueMilestoneEditActivity.class);
        intent.putExtra(Constants.Repository.OWNER, mRepoOwner);
        intent.putExtra(Constants.Repository.NAME, mRepoName);
        intent.putExtra(Constants.Milestone.NUMBER, milestone.getNumber());
        startActivity(intent);
    }

    @Override
    public Loader<LoaderResult<List<Milestone>>> onCreateLoader(int id, Bundle args) {
        return new MilestoneListLoader(getActivity(), mRepoOwner, mRepoName, mState);
    }
}