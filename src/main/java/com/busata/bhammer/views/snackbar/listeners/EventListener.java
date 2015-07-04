package com.busata.bhammer.views.snackbar.listeners;


import com.busata.bhammer.views.snackbar.Snackbar;

/**
 * Interface used to notify of all  display events. Useful if you want
 * to move other views while the Snackbar is on screen.
 */
public interface EventListener {
    /**
     * Called when a  is about to enter the screen
     *
     * @param snackbar the  that's being shown
     */
    public void onShow(Snackbar snackbar);

    /**
     * Called when a  is about to enter the screen while
     * a  is about to exit the screen by replacement.
     *
     * @param snackbar the  that's being shown
     */
    public void onShowByReplace(Snackbar snackbar);

    /**
     * Called when a  is fully shown
     *
     * @param snackbar the  that's being shown
     */
    public void onShown(Snackbar snackbar);

    /**
     * Called when a  is about to exit the screen
     *
     * @param snackbar the  that's being dismissed
     */
    public void onDismiss(Snackbar snackbar);

    /**
     * Called when a  is about to exit the screen
     * when a new  is about to enter the screen.
     *
     * @param snackbar the  that's being dismissed
     */
    public void onDismissByReplace(Snackbar snackbar);

    /**
     * Called when a  had just been dismissed
     *
     * @param snackbar the  that's being dismissed
     */
    public void onDismissed(Snackbar snackbar);
}
