// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.notifypatchset;

import com.google.gerrit.common.ChangeHooks;
import com.google.gerrit.extensions.restapi.AuthException;
import com.google.gerrit.extensions.restapi.BadRequestException;
import com.google.gerrit.extensions.restapi.ResourceConflictException;
import com.google.gerrit.extensions.restapi.RestReadView;
import com.google.gerrit.extensions.webui.UiAction;
import com.google.gerrit.reviewdb.server.ReviewDb;
import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.IdentifiedUser;
import com.google.gerrit.server.change.RevisionResource;
import com.google.gerrit.server.data.AccountAttribute;
import com.google.gerrit.server.data.ChangeAttribute;
import com.google.gerrit.server.data.PatchSetAttribute;
import com.google.gerrit.server.events.EventFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class NotifyPatchSetAction implements UiAction<RevisionResource>, RestReadView<RevisionResource> {
  private final Provider<CurrentUser> user;
  private final ChangeHooks changeHooks;
  private final EventFactory eventFactory;
  private final ReviewDb reviewDb;

  @Inject
  public NotifyPatchSetAction(
      Provider<CurrentUser> user,
      ChangeHooks changeHooks,
      EventFactory eventFactory,
      ReviewDb reviewDb) {
    this.user = user;
    this.changeHooks = changeHooks;
    this.eventFactory = eventFactory;
    this.reviewDb = reviewDb;
  }

  @Override
  public String apply(RevisionResource rrsc) throws AuthException, BadRequestException, ResourceConflictException,
  Exception {
    PatchSetNotifiedEvent event = new PatchSetNotifiedEvent();

    ChangeAttribute change = eventFactory.asChangeAttribute(rrsc.getChange());
    PatchSetAttribute patchSet = eventFactory.asPatchSetAttribute(rrsc.getPatchSet());
    AccountAttribute notifier = eventFactory.asAccountAttribute(((IdentifiedUser)user.get()).getAccountId());

    event.change = change;
    event.patchSet = patchSet;
    event.notifier = notifier;

    changeHooks.postEvent(rrsc.getChange(), event, reviewDb);

    return "Notified.";
  }

  @Override
  public com.google.gerrit.extensions.webui.UiAction.Description getDescription(RevisionResource rrsc) {
    return new Description()
    .setLabel("Notify")
    .setTitle("Notify this patchset.")
    .setVisible(user.get() instanceof IdentifiedUser);
  }

}
