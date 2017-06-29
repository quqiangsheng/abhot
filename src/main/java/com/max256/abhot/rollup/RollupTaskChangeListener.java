package com.max256.abhot.rollup;

public interface RollupTaskChangeListener
{
	enum Action
	{
		ADDED,
		CHANGED,
		REMOVED
	}

	void change(RollupTask task, Action action);

}
