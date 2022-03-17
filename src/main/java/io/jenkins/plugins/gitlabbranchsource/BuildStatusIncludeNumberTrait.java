package io.jenkins.plugins.gitlabbranchsource;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import jenkins.scm.api.SCMSource;
import jenkins.scm.api.trait.SCMSourceContext;
import jenkins.scm.api.trait.SCMSourceTrait;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class BuildStatusIncludeNumberTrait extends SCMSourceTrait {

    /**
     * Include the build number in the status name (e.g. 'jenkinsci/branch/3')
     */
    private boolean onName;

    /**
     * Include the build number in the status description
     * (e.g. 'jenkinsci/branch - Test/develop #3: This commit looks good.').
     */
    private boolean onDescription;

    /**
     * Constructor for stapler.
     *
     * @param strategyId the strategy id.
     */
    @DataBoundConstructor
    public BuildStatusIncludeNumberTrait(boolean onName, boolean onDescription) {
    	this.onName = onName;
    	this.onDescription = onDescription;
    }

    @Override
    protected void decorateContext(SCMSourceContext<?, ?> context) {
        if (context instanceof GitLabSCMSourceContext) {
            GitLabSCMSourceContext ctx = (GitLabSCMSourceContext) context;
            ctx.withBuildNumberInStatusName(getOnName());
            ctx.withBuildNumberInStatusDescription(getOnDescription());
        }
    }

    /**
     * Setter for stapler to enable the build number on the status name.
     */
    @DataBoundSetter
    public void setOnName(boolean enabled) {
        this.onName = enabled;
    }

    /**
     * Setter for stapler to enable the build number on the status description.
     */
    @DataBoundSetter
    public void setOnDescription(boolean enabled) {
        this.onDescription = enabled;
    }

    /**
     * Gets whether the build number should be set on the status name.
     *
     * @return the web hook mode of registration to apply.
     */
    public boolean getOnName() {
        return onName;
    }

    /**
     * Gets whether the build number should be set on the status description.
     *
     * @return the web hook mode of registration to apply.
     */
    public boolean getOnDescription() {
        return onDescription;
    }


    /**
     * Our descriptor.
     */
    @Extension
    @Symbol("buildStatusIncludeNumber")
    public static class DescriptorImpl extends SCMSourceTraitDescriptor {

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.buildStatusIncludeNumber_displayName();
        }

        @Override
        public Class<? extends SCMSourceContext> getContextClass() {
            return GitLabSCMSourceContext.class;
        }

        @Override
        public Class<? extends SCMSource> getSourceClass() {
            return GitLabSCMSource.class;
        }
    }
}
